package com.vhalitsyn.service.store;

import com.vhalitsyn.data.Playlist;
import com.vhalitsyn.data.Song;
import com.vhalitsyn.data.User;
import com.vhalitsyn.service.common.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonFileIteratorTests {
    @Test
    public void testIteratorWorksMultipleUse() throws IOException
    {
        final File f = ObjectFactory.getFile("mixtape-data-small.json");
        final JsonFileIterator<Song> songReader = new JsonFileIterator<>(f, Song.class, Song.SONGS_OBJECT_START);
        final JsonFileIterator<Playlist> playlistReader = new JsonFileIterator<>(f, Playlist.class, Song.SONGS_OBJECT_START);
        final JsonFileIterator<User> userReader = new JsonFileIterator<>(f, User.class, Song.SONGS_OBJECT_START);

        for (int i = 0; i < 2; i++)
        {
            Assert.assertTrue(songReader.iterator().hasNext());
            Assert.assertTrue(playlistReader.iterator().hasNext());
            Assert.assertTrue(userReader.iterator().hasNext());

            Assert.assertNotNull(songReader.iterator().next());
            Assert.assertNotNull(playlistReader.iterator().next());
            Assert.assertNotNull(userReader.iterator().next());
        }

        Assert.assertFalse(songReader.iterator().hasNext());
        Assert.assertFalse(playlistReader.iterator().hasNext());
        Assert.assertFalse(userReader.iterator().hasNext());

        songReader.close();
        playlistReader.close();
        userReader.close();
    }
}
