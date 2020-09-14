package com.moayo.server.model;

public class CategoryHashModel {
    private int co_dogamId;
    private int co_categoryId;
    private String co_hashtag;

    public CategoryHashModel(int co_dogamId, int co_categoryId, String co_hashtag) {
        this.co_dogamId = co_dogamId;
        this.co_categoryId = co_categoryId;
        this.co_hashtag = co_hashtag;
    }

    public CategoryHashModel() {
    }

    public void setco_hashtag(String co_hashtag) {
        this.co_hashtag = co_hashtag;
    }

    public String getco_hashtag() {
        return co_hashtag;
    }

    public void setco_categoryId(int co_categoryId) {
        this.co_categoryId = co_categoryId;
    }

    public int getco_categoryId() {
        return co_categoryId;
    }

    public void setco_dogamId(int co_dogamId) {
        this.co_dogamId = co_dogamId;
    }

    public int getco_dogamId() {
        return co_dogamId;
    }

    @Override
    public String toString() {
        return "CategoryHashModel{" +
                "co_dogamId=" + co_dogamId +
                ", co_categoryId=" + co_categoryId +
                ", co_hashtag='" + co_hashtag + '\'' +
                '}';
    }
}
