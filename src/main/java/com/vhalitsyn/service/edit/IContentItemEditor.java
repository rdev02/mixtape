package com.vhalitsyn.service.edit;

import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.EditAction;

public interface IContentItemEditor {
    boolean applyAction(ContentItem item, EditAction edit);
}
