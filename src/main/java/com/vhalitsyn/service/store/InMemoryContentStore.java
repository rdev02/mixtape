package com.vhalitsyn.service.store;

import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.DataType;
import com.vhalitsyn.service.IContentSource;
import com.vhalitsyn.service.IContentStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InMemoryContentStore implements IContentStore {
    Map<DataType, Map<Long, ContentItem>> memoryStore;

    public InMemoryContentStore(IContentSource provider)
    {
        memoryStore = new HashMap<>();
        preLoadAll(provider, DataType.User);
        preLoadAll(provider, DataType.Playlist);
        preLoadAll(provider, DataType.Song);
    }

    void preLoadAll(IContentSource provider, DataType dataType)
    {
        Objects.requireNonNull(provider);
        Objects.requireNonNull(dataType);

        if (!memoryStore.containsKey(dataType))
        {
            memoryStore.put(dataType, new HashMap<>());
        }

        final Map<Long, ContentItem> contentMap = memoryStore.get(dataType);
        provider.getData(dataType).forEach(ci -> {
            contentMap.put(ci.getId(), ci);
        });
    }

    @Override
    public Iterable<ContentItem> getData(DataType type)
    {
        Objects.requireNonNull(type);

        return memoryStore.get(type).values();
    }

    @Override
    public ContentItem getContentItem(long id, DataType type)
    {
        Objects.requireNonNull(type);

        return memoryStore.get(type).get(id);
    }

    @Override
    public ContentItem setContentItem(long id, ContentItem data)
    {
        Objects.requireNonNull(data);

        // add
        if(id == -1){
            return memoryStore.get(data.getType()).put(data.getId(), data);
        }

        return memoryStore.get(data.getType()).put(id, data);
    }

    @Override
    public ContentItem deleteContentItem(long id, DataType type)
    {
        Objects.requireNonNull(type);

        return memoryStore.get(type).remove(id);
    }
}
