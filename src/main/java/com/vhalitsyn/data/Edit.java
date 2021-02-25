package com.vhalitsyn.data;

import com.vhalitsyn.data.gson.GsonProvider;

import java.util.List;

public class Edit {
    public static final String EDITS_OBJECT_START = "edits";

    DataType targetType;
    long targetId;
    List<EditAction> editActions;

    public DataType getTargetType()
    {
        return targetType;
    }

    public void setTargetType(DataType targetType)
    {
        this.targetType = targetType;
    }

    public long getTargetId()
    {
        return targetId;
    }

    public void setTargetId(long targetId)
    {
        this.targetId = targetId;
    }

    public List<EditAction> getEditActions()
    {
        return editActions;
    }

    public void setEditActions(List<EditAction> editActions)
    {
        this.editActions = editActions;
    }


    public boolean isDelete()
    {
        return hasEditActionType(EditActionType.Remove);
    }

    public boolean isAdd()
    {
        return hasEditActionType(EditActionType.Add);
    }

    private boolean hasEditActionType(EditActionType remove)
    {
        if (editActions == null)
        {
            return false;
        }

        return editActions.stream().anyMatch(e -> e.getActionType().equals(remove));
    }
}
