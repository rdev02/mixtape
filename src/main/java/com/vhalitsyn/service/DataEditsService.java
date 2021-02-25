package com.vhalitsyn.service;

import com.vhalitsyn.data.*;
import com.vhalitsyn.service.edit.BaseEditor;
import com.vhalitsyn.service.edit.PlayListEditor;
import com.vhalitsyn.service.edit.SongEditor;
import com.vhalitsyn.service.edit.UserEditor;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataEditsService implements IContentEditsService {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(DataEditsService.class);

    IContentStore dataStore;
    Map<DataType, BaseEditor> editors;

    public DataEditsService(IContentStore aDataStore)
    {
        dataStore = aDataStore;
        editors = new HashMap<>();
        editors.put(DataType.User, new UserEditor());
        editors.put(DataType.Playlist, new PlayListEditor());
        editors.put(DataType.Song, new SongEditor());
    }

    @Override
    public ContentItem applyEdit(final Edit edit)
    {
        Objects.requireNonNull(edit);

        final DataType targetType = edit.getTargetType();
        final long targetId = edit.getTargetId();

        final BaseEditor contentEditor = editors.get(targetType);
        final ContentItem contentItem = dataStore.getContentItem(targetId, targetType);
        final ContentItem clone = contentItem.clone();
        edit.getEditActions().forEach(ea ->
        {
            final boolean actionApplied = contentEditor.applyAction(clone, ea);
            if (!actionApplied)
            {
                LOGGER.warn("edit {} was not applied on {} {}", ea, targetType, targetId);
            }
        });

        return clone;
    }
}
