package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.EditAction;
import com.vhalitsyn.data.Playlist;
import com.vhalitsyn.data.ContentItem;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayListEditor extends BaseEditor{
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
        final Playlist playlist = (Playlist) item;
        final String fieldName = edit.getFieldName();
        Objects.requireNonNull(fieldName);

        if (fieldName.equals("song_ids"))
        {
            String commaSeparatedSongIds = edit.getValue();
            final boolean addNew = commaSeparatedSongIds.startsWith("+");
            final boolean removeExisting = commaSeparatedSongIds.startsWith("-");
            if(addNew || removeExisting){
                commaSeparatedSongIds = commaSeparatedSongIds.substring(1);
            }
            final List<Long> songInds = Arrays.stream(commaSeparatedSongIds.split(",")).map(Long::parseLong).collect(Collectors.toList());

            if(addNew) {
                // TODO: verify songs exist, actually
                playlist.getSongIds().addAll(songInds);
            } else if(removeExisting) {
                playlist.getSongIds().removeAll(songInds);
            } else {
                playlist.setSongIds(songInds);
            }

            return true;
        }

        return false;
    }
}
