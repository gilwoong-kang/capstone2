package com.moayo.server.model;

public class CategoryModel {
    private int co_categoryId;
    private int co_dogamId;
    private String co_name;
    private int co_level;
    private int co_parentCategoryId;

    public CategoryModel(int co_categoryId, int co_dogamId, String co_name, int co_level, int co_parentCategoryId) {
        this.co_categoryId = co_categoryId;
        this.co_dogamId = co_dogamId;
        this.co_name = co_name;
        this.co_level = co_level;
        this.co_parentCategoryId = co_parentCategoryId;
    }

    public CategoryModel(int co_dogamId, String co_name, int co_level, int co_parentCategoryId) {
        this.co_dogamId = co_dogamId;
        this.co_name = co_name;
        this.co_level = co_level;
        this.co_parentCategoryId = co_parentCategoryId;
    }

    public CategoryModel(int co_dogamId, String co_name, int co_level) {
        this.co_dogamId = co_dogamId;
        this.co_name = co_name;
        this.co_level = co_level;
    }

    public CategoryModel() {
    }

    public int getCo_dogamId() {
        return co_dogamId;
    }

    public void setCo_dogamId(int co_dogamId) {
        this.co_dogamId = co_dogamId;
    }

    public int getCo_categoryId() {
        return co_categoryId;
    }

    public void setCo_categoryId(int co_categoryId) {
        this.co_categoryId = co_categoryId;
    }

    public int getCo_level() {
        return co_level;
    }

    public void setCo_level(int co_level) {
        this.co_level = co_level;
    }

    public int getCo_parentCategoryId() {
        return co_parentCategoryId;
    }

    public void setCo_parentCategoryId(int co_parentCategoryId) {
        this.co_parentCategoryId = co_parentCategoryId;
    }

    public String getCo_name() {
        return co_name;
    }

    public void setCo_name(String co_name) {
        this.co_name = co_name;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "co_categoryId=" + co_categoryId +
                ", co_dogamId=" + co_dogamId +
                ", co_name='" + co_name + '\'' +
                ", co_level=" + co_level +
                ", co_parentCategoryId=" + co_parentCategoryId +
                '}';
    }
}
