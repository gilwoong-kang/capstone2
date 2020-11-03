package com.moayo.server.model;
/**
 * 카테고리와 해시태그값은 n:m의 관계를 가지기에 1:n관계를 위한 추가 테이블이 필요.
 * 해당 클래스는 그 추가 테이블 값을 매핑하기 위한 model.
 *
 * @author gilwoongkang
 * */
public class CategoryHashModel {
    /** 해당 카테고리 및 해시값이 속한 도감의 id */
    private int co_dogamId;

    /** 해시태그과 연결된 카테고리의 id */
    private int co_categoryId;

    /** 카테고리와 연결된 해시태그 */
    private String co_hashtag;

    /**
     * Constructor는 값이 모두 존재하거나 값이 없는 경우만을 상정하므로 해당 경우에 맞게 설정.
     * */
    public CategoryHashModel(int co_dogamId, int co_categoryId, String co_hashtag) {
        this.co_dogamId = co_dogamId;
        this.co_categoryId = co_categoryId;
        this.co_hashtag = co_hashtag;
    }

    public CategoryHashModel() {
    }

    /**
     * get/set 메소드
     * */
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

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return 카테고리-해시 테이블에 존재하는 하나의 row값을 String값으로 반환한다.
     */
    @Override
    public String toString() {
        return "CategoryHashModel{" +
                "co_dogamId=" + co_dogamId +
                ", co_categoryId=" + co_categoryId +
                ", co_hashtag='" + co_hashtag + '\'' +
                '}';
    }
}
