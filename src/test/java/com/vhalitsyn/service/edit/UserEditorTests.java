package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.EditActionType;
import com.vhalitsyn.service.common.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class UserEditorTests {
    @Test
    public void applyActionTest(){
        UserEditor e = new UserEditor();
        boolean actionApplied = e.applyAction(ObjectFactory.newUser(), ObjectFactory.newEditAction(EditActionType.Remove));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newUser(), ObjectFactory.newEditAction(EditActionType.SetField, "name"));
        Assert.assertTrue(actionApplied);

        actionApplied = e.applyAction(ObjectFactory.newUser(), ObjectFactory.newEditAction(EditActionType.SetField, "bogus"));
        Assert.assertFalse(actionApplied);
    }

}
