package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.EditAction;
import com.vhalitsyn.data.Song;

import java.util.Objects;

public class SongEditor extends BaseEditor {
    @Override
    public boolean applyAction(ContentItem item, EditAction edit)
    {
        Objects.requireNonNull(item);
        Objects.requireNonNull(edit);

        switch (edit.getActionType())
        {
            case SetField:
                return applyField(item, edit);
            default:
                return super.applyAction(item, edit);
        }
    }

    private boolean applyField(ContentItem item, EditAction edit)
    {
        final Song song = (Song) item;
        final String fieldName = edit.getFieldName();
        Objects.requireNonNull(fieldName);

        if (fieldName.equals("artist"))
        {
            song.setArtist(edit.getValue());
            return true;
        } else if (fieldName.equals("title"))
        {
            song.setTitle(edit.getValue());
            return true;
        }

        return false;
    }
}
