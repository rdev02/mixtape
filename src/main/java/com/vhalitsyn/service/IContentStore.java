package com.vhalitsyn.service;

import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.DataType;

public interface IContentStore extends IContentSource {
    ContentItem getContentItem(long id, DataType type);
    ContentItem setContentItem(long id, ContentItem data);
    ContentItem deleteContentItem(long id, DataType type);
}
