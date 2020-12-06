package com.moayo.server.model;

public class CategoryModel {

    private int categoryId;
    private int dogamId;
    private String name;
    private int level;
    private int parentCategoryId;

    public CategoryModel(int categoryId, int dogamId, String name, int level, int parentCategoryId) {
        this.categoryId = categoryId;
        this.dogamId = dogamId;
        this.name = name;
        this.level = level;
        this.parentCategoryId = parentCategoryId;
    }

    public CategoryModel(int dogamId, String name, int level, int parentCategoryId) {
        this.dogamId = dogamId;
        this.name = name;
        this.level = level;
        this.parentCategoryId = parentCategoryId;
    }

    public CategoryModel(int dogamId, String name, int level) {
        this.dogamId = dogamId;
        this.name = name;
        this.level = level;
    }

    public CategoryModel() { }

    public int getDogamId() {
        return dogamId;
    }

    public void setDogamId(int dogamId) {
        this.dogamId = dogamId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return
     */
    @Override
    public String toString() {
        return "CategoryModel{" +
                "categoryId=" + categoryId +
                ", dogamId=" + dogamId +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", parentCategoryId=" + parentCategoryId +
                '}';
    }
}
