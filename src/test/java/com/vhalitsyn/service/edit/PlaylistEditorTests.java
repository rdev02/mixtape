package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.EditAction;
import com.vhalitsyn.data.EditActionType;
import com.vhalitsyn.data.Playlist;
import com.vhalitsyn.service.common.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class PlaylistEditorTests {
    @Test
    public void applyActionTest(){
        PlayListEditor e = new PlayListEditor();
        boolean actionApplied = e.applyAction(ObjectFactory.newPlaylist(), ObjectFactory.newEditAction(EditActionType.Remove));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newPlaylist(), new EditAction(EditActionType.SetField, "song_ids", "1,2,3"));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newPlaylist(), ObjectFactory.newEditAction(EditActionType.SetField, "bogus"));
        Assert.assertFalse(actionApplied);
    }

    @Test
    public void applyActionEditSongListTest(){
        PlayListEditor e = new PlayListEditor();

        EditAction editSongListAction = new EditAction(EditActionType.SetField, "song_ids", "1,2,3");
        final Playlist playlist = ObjectFactory.newPlaylist();
        boolean actionApplied = e.applyAction(playlist, editSongListAction);
        Assert.assertTrue(actionApplied);
        Assert.assertTrue(playlist.getSongIds().contains(1L));
        Assert.assertTrue(playlist.getSongIds().contains(2L));
        Assert.assertTrue(playlist.getSongIds().contains(3L));

        editSongListAction = new EditAction(EditActionType.SetField, "song_ids", "-3,4");
        actionApplied = e.applyAction(playlist, editSongListAction);
        Assert.assertTrue(actionApplied);
        Assert.assertTrue(playlist.getSongIds().contains(1L));
        Assert.assertTrue(playlist.getSongIds().contains(2L));
        Assert.assertFalse(playlist.getSongIds().contains(3L));

        editSongListAction = new EditAction(EditActionType.SetField, "song_ids", "+103,104");
        actionApplied = e.applyAction(playlist, editSongListAction);
        Assert.assertTrue(actionApplied);
        Assert.assertTrue(playlist.getSongIds().contains(1L));
        Assert.assertTrue(playlist.getSongIds().contains(2L));
        Assert.assertTrue(playlist.getSongIds().contains(103L));
        Assert.assertTrue(playlist.getSongIds().contains(104L));
    }

}
