package com.vhalitsyn.service.common;

import com.vhalitsyn.data.*;
import com.vhalitsyn.service.IContentSource;
import com.vhalitsyn.service.IContentStore;
import com.vhalitsyn.service.store.InMemoryContentStore;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ObjectFactory {

    public static IContentSource newContentSource()
    {
        return new MockContentProvider();
    }

    public static IContentStore newContentStore(){
        return new InMemoryContentStore(newContentSource());
    }

    public static Song newSong()
    {
        return new Song(7, "test artist", "test title");
    }

    public static User newUser()
    {
        return new User(8, "test user");
    }

    public static Playlist newPlaylist()
    {
        List<Long> songs = new LinkedList<>();
        songs.add(1L);
        songs.add(5L);
        songs.add(11L);
        return new Playlist(9, 10, songs);
    }

    public static EditAction newEditAction(EditActionType type)
    {
        final EditAction editAction = new EditAction(type, "", "");
        return editAction;
    }

    public static EditAction newEditAction(EditActionType type, String fieldName)
    {
        final EditAction editAction = new EditAction(type, fieldName, "test value");
        return editAction;
    }

    static class MockContentProvider implements IContentSource {
        @Override
        public Iterable<ContentItem> getData(DataType type)
        {
            switch (type)
            {
                case Playlist:
                    return getContentItemList(ObjectFactory::newPlaylist);
                case Song:
                    return getContentItemList(ObjectFactory::newSong);
                case User:
                    return getContentItemList(ObjectFactory::newUser);
            }

            throw new RuntimeException(type + " type not implemented");
        }
    }

    static List<ContentItem> getContentItemList(Supplier<ContentItem> supplier)
    {
        final List<ContentItem> lst = new LinkedList<>();
        lst.add(supplier.get());
        return lst;
    }

    public static File getFile(String fName){
        final URL url = Thread.currentThread().getContextClassLoader().getResource(fName);
        return new File(url.getPath());
    }
}
