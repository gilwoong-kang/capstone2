package com.moayo.server.model;

public class CategoryPostModel {
    private int co_dogamId;
    private int co_categoryId;
    private int co_postId;


    public CategoryPostModel(int co_dogamId, int co_categoryId, int co_postId) {
        this.co_dogamId = co_dogamId;
        this.co_categoryId = co_categoryId;
        this.co_postId = co_postId;
    }

    public CategoryPostModel() {
    }

    public void setCo_dogamId(int co_dogamId) {
        this.co_dogamId = co_dogamId;
    }

    public int getCo_categoryId() {
        return co_categoryId;
    }

    public int getCo_postId() {
        return co_postId;
    }

    public int getCo_dogamId() {
        return co_dogamId;
    }

    public void setCo_categoryId(int co_categoryId) {
        this.co_categoryId = co_categoryId;
    }

    public void setCo_postId(int co_postId) {
        this.co_postId = co_postId;
    }

    @Override
    public String toString() {
        return "CategoryPostModel{" +
                "co_dogamId=" + co_dogamId +
                ", co_categoryId=" + co_categoryId +
                ", co_postId=" + co_postId +
                '}';
    }
}
