package com.moayo.server.model;

public class CategoryPostModel {

    private int co_dogamId;
    private int co_categoryId;
    private int co_postId;

    /**
     * Constructor는 모든 값이 존재하거나 값이 없어야만 유효.
     * 데이터베이스에서 값이 참조를 받기 때문임.
     * @param co_dogamId 해당 값들을 포함하는 도감의 id.
     * @param co_categoryId row의 카테고리 id
     * @param co_postId row의 게시물 id
     */
    public CategoryPostModel(int co_dogamId, int co_categoryId, int co_postId) {
        this.co_dogamId = co_dogamId;
        this.co_categoryId = co_categoryId;
        this.co_postId = co_postId;
    }

    public CategoryPostModel() { }

    /**
     * get/set 메소드
     * */
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

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return
     */
    @Override
    public String toString() {
        return "CategoryPostModel{" +
                "co_dogamId=" + co_dogamId +
                ", co_categoryId=" + co_categoryId +
                ", co_postId=" + co_postId +
                '}';
    }
}
