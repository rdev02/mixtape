package com.vhalitsyn.data;

import com.google.gson.annotations.SerializedName;

public enum DataType {
    @SerializedName("user")
    User(com.vhalitsyn.data.User.USERS_OBJECT_START, com.vhalitsyn.data.User.class),
    @SerializedName("playlist")
    Playlist(com.vhalitsyn.data.Playlist.PLAYLISTS_OBJECT_START, com.vhalitsyn.data.Playlist.class),
    @SerializedName("song")
    Song(com.vhalitsyn.data.Song.SONGS_OBJECT_START,com.vhalitsyn.data.Song.class);

    DataType(String dataObjectSerializedFieldName, Class dataObjectSerializedClass)
    {
        this.dataObjectSerializedFieldName = dataObjectSerializedFieldName;
        this.dataObjectSerializedClass = dataObjectSerializedClass;
    }

    String dataObjectSerializedFieldName;
    Class dataObjectSerializedClass;

    public String getDataObjectSerializedFieldName()
    {
        return dataObjectSerializedFieldName;
    }

    public Class getDataObjectSerializedClass()
    {
        return dataObjectSerializedClass;
    }
}
