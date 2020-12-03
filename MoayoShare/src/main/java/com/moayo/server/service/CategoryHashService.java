package com.moayo.server.service;


import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.CategoryModel;

public interface CategoryHashService {
    public CategoryHashModel[] getCategoryHash(int dogamId);

    void labelingCategoryHash(CategoryHashModel[] categoryHashModels, int origin, CategoryModel categoryModel);

    int insertCategoryHash(CategoryHashModel[] categoryHashModels);
}
