package com.moayo.server.service;

import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.HashtagModel;

public interface HashService {
    public HashtagModel[] getHashtag(int dogamId, CategoryHashModel[] categoryHashModels);
}
