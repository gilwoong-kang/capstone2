package com.moayo.server.model;

/**
 * '#'을 포함하지 않음.
 */
public class HashtagModel {

    private String hashtag;

    public HashtagModel(String hashtag) {
        this.hashtag = hashtag;
    }

    public HashtagModel() { }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getHashtag() {
        return hashtag;
    }

    @Override
    public String toString() {
        return "HashtagModel{" +
                "hashtag='" + hashtag + '\'' +
                '}';
    }
}
