package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.EditAction;
import com.vhalitsyn.data.ContentItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public abstract class BaseEditor implements IContentItemEditor {
    private static final Logger LOGGER = LogManager.getLogger(BaseEditor.class);

    public boolean applyAction(ContentItem item, EditAction edit){
        Objects.requireNonNull(edit);

        ContentItem previousItem;
        switch (edit.getActionType()){
            case Remove:
                // always support removal
                return true;
            default:
                return false;
        }
    }
}
