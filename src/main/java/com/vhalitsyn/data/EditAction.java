package com.vhalitsyn.data;


public class EditAction {
    EditActionType actionType;
    String fieldName;
    String value;

    public EditAction()
    {
    }

    public EditAction(EditActionType actionType, String fieldName, String value)
    {
        this.actionType = actionType;
        this.fieldName = fieldName;
        this.value = value;
    }

    public EditActionType getActionType()
    {
        return actionType;
    }

    public String getValue()
    {
        return value;
    }

    public String getFieldName()
    {
        return fieldName;
    }
}

