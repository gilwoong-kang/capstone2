package com.moayo.server.model;

public class PostModel {

    private int postId;
    private String postUrl;
    private String imageUrl;
    private String hashtag;
    private int like;

    public PostModel(String postUrl, String imageUrl, String hashtag, int like) {
        this.postUrl = postUrl;
        this.imageUrl = imageUrl;
        this.hashtag = hashtag;
        this.like = like;
    }

    public PostModel(int postId, String postUrl, String imageUrl, String hashtag, int like) {
        this.postId = postId;
        this.postUrl = postUrl;
        this.imageUrl = imageUrl;
        this.hashtag = hashtag;
        this.like = like;
    }

    public PostModel() { }

    /**
     * get/set 메소드.
     */
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return 게시물과 관련된 정보를 JSON형태에 맞게 리턴.
     */
    @Override
    public String toString() {
        return "PostModel{" +
                "postId=" + postId +
                ", postUrl='" + postUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", hashtag='" + hashtag + '\'' +
                ", like=" + like +
                '}';
    }
}
