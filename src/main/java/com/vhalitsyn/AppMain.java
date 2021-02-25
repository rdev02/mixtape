package com.vhalitsyn;


import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.DataType;
import com.vhalitsyn.data.Edit;
import com.vhalitsyn.data.EditActionType;
import com.vhalitsyn.data.gson.GsonProvider;
import com.vhalitsyn.service.*;
import com.vhalitsyn.service.store.FileDataSource;
import com.vhalitsyn.service.store.InMemoryContentStore;
import com.vhalitsyn.service.store.JsonFileIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static com.vhalitsyn.data.Edit.EDITS_OBJECT_START;

public class AppMain {
    private static final Logger LOGGER = LogManager.getLogger(AppMain.class);

    public static void main(String[] args) throws IOException
    {
        LOGGER.info("Process startup");

        final CliOptions options = new CliOptions();
        setDefaultOptions(options);
        try
        {
            parseArgs(args, options);
            validateOptions(options);
        } catch (RuntimeException rex)
        {
            LOGGER.fatal("Exception initializing", rex);
            return;
        }

        LOGGER.info("Initializing data: {}", options.getInputFile());
        final IContentStore ds = new InMemoryContentStore(new FileDataSource(options.getInputFile()));

        LOGGER.info("Applying changes: {}", options.getEditsFile());
        final IContentEditsService editsService = new DataEditsService(ds);
        try (final JsonFileIterator<Edit> editsIterator = new JsonFileIterator<>(options.getEditsFile(), Edit.class, EDITS_OBJECT_START))
        {
            editsIterator.forEach(edit -> applyAndPersist(editsService, ds, edit));
        }

        LOGGER.info("Finalizing data: {}", options.getOutputFile());
        final FileDataSource serializer = new FileDataSource(options.getOutputFile());
        serializer.serializeToFile(ds);

        LOGGER.info("Process exiting");
    }

    static void applyAndPersist(IContentEditsService editsService, IContentStore ds, Edit edit)
    {
        final long targetId = edit.getTargetId();

        // handle delete specially. It's unclear if (other) changes need to be persisted before removal.
        // if so - here is the place to do it.
        // for now assume that delete instruction has the highest priority
        final DataType targetType = edit.getTargetType();
        if (edit.isDelete())
        {
            ds.deleteContentItem(targetId, targetType);
        } else if (edit.isAdd())
        {
            final ContentItem newObject = (ContentItem) GsonProvider.getGSON().fromJson(edit.getEditActions().get(0).getValue(), targetType.getDataObjectSerializedClass());
            // TODO: we may not trust the added data id, or we may trust that DS knows how to generate ids for the new items.
            // if we want special handling for add, we may want to implement a separate Add method on the IDataStore
            // that would generate an available id, assign it to the object and persist.
            ds.setContentItem(edit.getTargetId(), newObject);
        } else
        {
            // apply, side-effect free
            final ContentItem contentItem = editsService.applyEdit(edit);
            // choose to persist immediately
            ds.setContentItem(targetId, contentItem);
        }
    }

    static void setDefaultOptions(CliOptions options)
    {
        options.setInputFile(new File("mixtape-data.json"));
        options.setEditsFile(new File("mixtape-edits.json"));
        options.setOutputFile(new File("mixtape-output.json"));
    }

    static void validateOptions(CliOptions options)
    {
        parseFilePathToArg(options.getInputFile(), true, false);
        parseFilePathToArg(options.getEditsFile(), true, false);
        parseFilePathToArg(options.getOutputFile(), false, true);
    }

    static void parseArgs(String[] args, CliOptions options)
    {
        for (int i = 0; i < args.length; i++)
        {
            String arg = args[i];
            final File path = new File(arg.substring(arg.indexOf("=") + 1));
            if (arg.startsWith("--input="))
            {
                options.setInputFile(path);
            } else if (arg.startsWith("--edits="))
            {
                options.setEditsFile(path);
            } else if (arg.startsWith("--output="))
            {
                options.setOutputFile(path);
            } else
            {
                LOGGER.warn("unrecognized parameter {}", arg);
            }
        }
    }

    static void parseFilePathToArg(File parsedFile, boolean mustExist, boolean needWrite)
    {
        if (mustExist && (!parsedFile.exists() || !parsedFile.canRead()))
        {
            throw new RuntimeException(String.format("%s does not exist or can't be read.", parsedFile));
        }

        if (needWrite && parsedFile.exists() && !parsedFile.canWrite())
        {
            throw new RuntimeException(String.format("can not write to %s.", parsedFile));
        }
    }
}
