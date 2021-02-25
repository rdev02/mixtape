package com.vhalitsyn.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON serialization POJO
 */
public class Playlist extends ContentItem {
    public static final String PLAYLISTS_OBJECT_START = "playlists";

    @SerializedName("user_id")
    long userId;

    @SerializedName("song_ids")
    List<Long> songIds;

    public Playlist(long id, long userId, List<Long> songIds)
    {
        super(id);
        this.userId = userId;
        this.songIds = new ArrayList<>(songIds);
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public List<Long> getSongIds()
    {
        return songIds;
    }

    public void setSongIds(List<Long> songIds)
    {
        this.songIds = songIds;
    }

    public ContentItem clone()
    {
        return new Playlist(id, userId, songIds);
    }

    @Override
    public DataType getType()
    {
        return DataType.Playlist;
    }
}
