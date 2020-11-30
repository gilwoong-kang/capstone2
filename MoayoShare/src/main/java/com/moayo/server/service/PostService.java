package com.moayo.server.service;

import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.model.PostModel;

public interface PostService {
    public PostModel[] getPost(int dogamId, CategoryPostModel[] categoryPostModels);
}
