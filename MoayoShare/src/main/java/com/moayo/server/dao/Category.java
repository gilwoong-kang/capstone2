package com.moayo.server.dao;

import com.moayo.server.model.CategoryModel;


public interface Category {

    long insertCategory(CategoryModel categoryModel);
    long updateCategory(CategoryModel categoryModel);
    long deleteCategory(CategoryModel categoryModel);
    long deleteCategoryById(int id,int dogamId);
    long deleteCategoryByDogamId(int dogamId);
    CategoryModel getCategoryById(int id,int dogamId);
    CategoryModel[] getCategoryByDogamId(int dogamId);
    void foreignKeyON();
    void foreignKeyOFF();
}
