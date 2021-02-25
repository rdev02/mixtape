package com.vhalitsyn.data;

/**
 * JSON serialization POJO
 */
public class Song extends ContentItem {
    public static final String SONGS_OBJECT_START = "songs";

    String artist;
    String title;

    public ContentItem clone()
    {
        return new Song(id, artist, title);
    }

    public Song(long id, String artist, String title)
    {
        super(id);
        this.artist = artist;
        this.title = title;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public DataType getType()
    {
        return DataType.Song;
    }
}
