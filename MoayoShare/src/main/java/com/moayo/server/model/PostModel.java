package com.moayo.server.model;

public class PostModel {

    private int co_postId;
    private String co_postUrl;
    private String co_imageUrl;
    private String co_hashtag;
    private int co_like;

    public PostModel(int co_postId, String co_postUrl, String co_imageUrl, String co_hashtag, int co_like) {
        this.co_postId = co_postId;
        this.co_postUrl = co_postUrl;
        this.co_imageUrl = co_imageUrl;
        this.co_hashtag = co_hashtag;
        this.co_like = co_like;
    }

    public PostModel(String co_postUrl, String co_imageUrl, String co_hashtag, int co_like) {
        this.co_postUrl = co_postUrl;
        this.co_imageUrl = co_imageUrl;
        this.co_hashtag = co_hashtag;
        this.co_like = co_like;
    }

    public PostModel() { }

    /**
     * get/set 메소드.
     */
    public int getCo_like() {
        return co_like;
    }

    public void setCo_like(int co_like) {
        this.co_like = co_like;
    }

    public String getCo_hashtag() {
        return co_hashtag;
    }

    public void setCo_hashtag(String co_hashtag) {
        this.co_hashtag = co_hashtag;
    }

    public int getCo_postId() {
        return co_postId;
    }

    public void setCo_postId(int co_postId) {
        this.co_postId = co_postId;
    }

    public String getCo_imageUrl() {
        return co_imageUrl;
    }

    public void setCo_imageUrl(String co_imageUrl) {
        this.co_imageUrl = co_imageUrl;
    }

    public String getCo_postUrl() {
        return co_postUrl;
    }

    public void setCo_postUrl(String co_postUrl) {
        this.co_postUrl = co_postUrl;
    }

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return 게시물과 관련된 정보를 JSON형태에 맞게 리턴.
     */
    @Override
    public String toString() {
        return "PostModel{" +
                "co_postId=" + co_postId +
                ", co_postUrl='" + co_postUrl + '\'' +
                ", co_imageUrl='" + co_imageUrl + '\'' +
                ", co_hashtag='" + co_hashtag + '\'' +
                ", co_like=" + co_like +
                '}';
    }
}
