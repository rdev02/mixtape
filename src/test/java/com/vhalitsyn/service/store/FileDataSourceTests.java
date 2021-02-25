package com.vhalitsyn.service.store;

import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.DataType;
import com.vhalitsyn.service.common.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.Iterator;

public class FileDataSourceTests {

    @Test
    public void testReadFromFile()
    {
        FileDataSource fileDataSource = new FileDataSource(ObjectFactory.getFile("mixtape-data-small.json"));
        final Iterable<ContentItem> data = fileDataSource.getData(DataType.User);

        int i = 0;
        for (ContentItem ci: data)
        {
            Assert.assertNotNull(ci); i++;
        }

        Assert.assertEquals(2, i);
    }

    @Test
    public void testSerializeToFile() throws IOException
    {
        final File tempFile = File.createTempFile("mixtape", "test");
        try
        {
            FileDataSource fileDataSource = new FileDataSource(tempFile);
            fileDataSource.serializeToFile(ObjectFactory.newContentStore());

            Assert.assertEquals("{\"users\":[{\"name\":\"test user\",\"id\":8}],\"playlists\":[{\"user_id\":10,\"song_ids\":[1,5,11],\"id\":9}],\"songs\":[{\"artist\":\"test artist\",\"title\":\"test title\",\"id\":7}]}",
                    Files.readString(tempFile.toPath()));
        }finally {
            if(tempFile != null) {
                tempFile.deleteOnExit();
            }
        }
    }
}
