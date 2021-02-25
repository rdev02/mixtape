package com.vhalitsyn.data;

public abstract class ContentItem {
    protected long id;

    public ContentItem(long aId)
    {
        id = aId;
    }

    public long getId()
    {
        return id;
    }

    public abstract DataType getType();

    public abstract ContentItem clone();
}
