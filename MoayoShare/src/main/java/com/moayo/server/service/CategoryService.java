package com.moayo.server.service;

import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.CategoryModel;
import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.model.DogamInfoModel;

import java.util.Map;

public interface CategoryService {
    public CategoryModel[] getCategory(int dogamId);

    Map<Integer,CategoryModel> insertCategory(CategoryModel[] categoryModels, DogamInfoModel dogamInfoModel,
                                              CategoryPostModel[] categoryPostModels, CategoryHashModel[] categoryHashModels);
}
