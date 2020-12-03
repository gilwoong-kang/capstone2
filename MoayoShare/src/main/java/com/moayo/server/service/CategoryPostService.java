package com.moayo.server.service;

import com.moayo.server.model.CategoryModel;
import com.moayo.server.model.CategoryPostModel;

public interface CategoryPostService {
    public CategoryPostModel[] getCategoryPost(int dogamId);

    void labelingCategoryPost(CategoryPostModel[] categoryPostModels, int origin, CategoryModel categoryModel);

    int insertCategoryPost(CategoryPostModel[] categoryPostModels);
}
