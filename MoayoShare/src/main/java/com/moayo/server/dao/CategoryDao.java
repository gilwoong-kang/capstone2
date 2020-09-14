package com.moayo.server.dao;

import com.moayo.server.model.CategoryModel;

import java.util.List;

public interface CategoryDao {
    // SET FOREIGN_KEY_CHECKS=1;
    long insertCategory(CategoryModel categoryModel);
    long rootInsert(CategoryModel categoryModel);
    long updateCategory(CategoryModel categoryModel);
    long deleteCategory(CategoryModel categoryModel);
    long deleteCategoryById(int id,int dogamId);
    long deleteCategoryByDogamId(int dogamId);
    CategoryModel getCategoryById(int id,int dogamId);
    CategoryModel[] getCategoryByDogamId(int dogamId);


    void foreignKeyON();
    void foreignKeyOFF();
}
