package com.moayo.server.model;

public class CategoryPostModel {

    private int dogamId;
    private int categoryId;
    private int postId;

    /**
     * Constructor는 모든 값이 존재하거나 값이 없어야만 유효.
     * 데이터베이스에서 값이 참조를 받기 때문임.
     * @param dogamId 해당 값들을 포함하는 도감의 id.
     * @param categoryId row의 카테고리 id
     * @param postId row의 게시물 id
     */
    public CategoryPostModel(int dogamId, int categoryId, int postId) {
        this.dogamId = dogamId;
        this.categoryId = categoryId;
        this.postId = postId;
    }

    public CategoryPostModel() { }

    /**
     * get/set 메소드
     * */
    public void setDogamId(int dogamId) {
        this.dogamId = dogamId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getPostId() {
        return postId;
    }

    public int getDogamId() {
        return dogamId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return
     */
    @Override
    public String toString() {
        return "CategoryPostModel{" +
                "dogamId=" + dogamId +
                ", categoryId=" + categoryId +
                ", postId=" + postId +
                '}';
    }
}
