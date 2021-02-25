package com.vhalitsyn.data;

/**
 * JSON serialization POJO
 */
public class User extends ContentItem {
    public static final String USERS_OBJECT_START = "users";

    String name;

    public User(long id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ContentItem clone()
    {
        return new User(id, name);
    }

    @Override
    public DataType getType()
    {
        return DataType.User;
    }
}
