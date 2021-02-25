package com.vhalitsyn.service;

import com.vhalitsyn.AppMain;
import com.vhalitsyn.service.common.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AppMainTests {

    @Test
    public void AppMainIntegrationTest()
    {
        final File inFile = ObjectFactory.getFile("mixtape-data-small.json");
        final File editFile = ObjectFactory.getFile("mixtape-edits.json");
        File outFile = null;
        try
        {
            outFile = File.createTempFile("mixtape", "test");
            AppMain.main(new String[]{
                    "--input=" + inFile.getAbsolutePath(),
                    "--output=" + outFile.getAbsolutePath(),
                    "--edits=" + editFile.getAbsolutePath()
            });

            Assert.assertEquals("{\"users\":[{\"name\":\"test edited name\",\"id\":1}],\"playlists\":[{\"user_id\":3,\"song_ids\":[10,13],\"id\":202}],\"songs\":[{\"artist\":\"New test artist\",\"title\":\"New Song title\",\"id\":2}]}",
                    Files.readString(outFile.toPath()));
        } catch (IOException ex){
            Assert.fail(ex.getMessage());
        } finally {
            if(outFile != null)
            {
                outFile.deleteOnExit();
            }
        }
    }
}
