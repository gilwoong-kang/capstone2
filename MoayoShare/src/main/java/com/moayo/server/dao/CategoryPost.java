package com.moayo.server.dao;

import com.moayo.server.model.CategoryPostModel;

public interface CategoryPost {

    long insertCategoryPost(CategoryPostModel categoryPostModel);
    long insertAll(CategoryPostModel[] categoryPostModels);
    long deleteByDogamId(int dogamId);
    long deleteByCategoryId(int categoryId,int dogamId);
    long deleteByPostId(String postId);
    CategoryPostModel[] getByDogamId(int dogamId);
    CategoryPostModel getByCategoryId(int categoryId,int dogamId);
    CategoryPostModel getByPostId(int postId);
}
