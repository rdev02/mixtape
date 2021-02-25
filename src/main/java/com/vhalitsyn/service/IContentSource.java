package com.vhalitsyn.service;

import com.vhalitsyn.data.ContentItem;
import com.vhalitsyn.data.DataType;

public interface IContentSource {
    Iterable<ContentItem> getData(DataType type);
}
