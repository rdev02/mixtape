package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.EditActionType;
import com.vhalitsyn.service.common.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class SongEditorTests {
    @Test
    public void applyActionTest(){
        SongEditor e = new SongEditor();
        boolean actionApplied = e.applyAction(ObjectFactory.newSong(), ObjectFactory.newEditAction(EditActionType.Remove));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newSong(), ObjectFactory.newEditAction(EditActionType.SetField, "title"));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newSong(), ObjectFactory.newEditAction(EditActionType.SetField, "artist"));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newSong(), ObjectFactory.newEditAction(EditActionType.SetField, "bogus"));
        Assert.assertFalse(actionApplied);
    }

}
