package com.vhalitsyn.service.store;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.DataType;
import com.vhalitsyn.data.gson.GsonProvider;
import com.vhalitsyn.service.IContentSource;
import com.vhalitsyn.service.IContentStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Objects;

public class FileDataSource implements IContentSource {
    private static final Logger LOGGER = LogManager.getLogger(FileDataSource.class);
    File dataFile;

    public FileDataSource(File aDataFile)
    {
        this.dataFile = aDataFile;
    }

    @Override
    public Iterable<ContentItem> getData(DataType type)
    {
        try
        {
            return new JsonFileIterator<ContentItem>(dataFile, type.getDataObjectSerializedClass(), type.getDataObjectSerializedFieldName());
        } catch (IOException e)
        {
            throw new RuntimeException("could not create playlist file reading iterator", e);
        }
    }

    public void serializeToFile(IContentStore store)
    {
        Objects.requireNonNull(store);

        LOGGER.debug("serializing {} to file {}", store, dataFile);
        final Gson gson = GsonProvider.getGSON();
        try (JsonWriter writer = gson.newJsonWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile)))))
        {
            writer.beginObject();

            serializeCollection(gson, writer, store, DataType.User);
            serializeCollection(gson, writer, store, DataType.Playlist);
            serializeCollection(gson, writer, store, DataType.Song);

            writer.endObject();
        } catch (IOException e)
        {
            throw new RuntimeException("error serializing", e);
        }

        LOGGER.info("serialization to {} complete", dataFile);
    }

    void serializeCollection(final Gson gson, final JsonWriter writer, IContentStore store, final DataType type) throws IOException
    {
        LOGGER.trace("serializing {}", type);
        final Iterable<ContentItem> data = store.getData(type);

        final Class dataObjectSerializedClass = type.getDataObjectSerializedClass();
        writer.name(type.getDataObjectSerializedFieldName());
        writer.beginArray();

        data.forEach(i -> {
            gson.toJson(i, dataObjectSerializedClass, writer);
        });

        writer.endArray();
        LOGGER.debug("serialization of {} complete", type);
    }
}
