package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.EditAction;
import com.vhalitsyn.data.User;

import java.util.Objects;

public class UserEditor extends BaseEditor {
    @Override
    public boolean applyAction(ContentItem item, EditAction edit)
    {
        Objects.requireNonNull(item);
        Objects.requireNonNull(edit);

        switch(edit.getActionType()){
            case SetField:
                return applyField(item, edit);
            default:
                return super.applyAction(item, edit);
        }
    }

    private boolean applyField(ContentItem item, EditAction edit)
    {
        final User user = (User)item;
        final String fieldName = edit.getFieldName();
        Objects.requireNonNull(fieldName);

        if(fieldName.equals("name")){
            user.setName(edit.getValue());
            return true;
        }

        return false;
    }
}
