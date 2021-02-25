package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.EditActionType;
import com.vhalitsyn.service.common.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class BaseEditorTests {
    @Test
    public void applyActionTest(){
        MockEditor e = new MockEditor();
        boolean actionApplied = e.applyAction(ObjectFactory.newSong(), ObjectFactory.newEditAction(EditActionType.Remove));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newSong(), ObjectFactory.newEditAction(EditActionType.SetField));
        Assert.assertFalse(actionApplied);
    }

    class MockEditor extends BaseEditor {

    }
}
