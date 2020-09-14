package com.moayo.server.model;

public class HashtagModel {
    private String co_hashtag;

    public HashtagModel(String co_hashtag) {
        this.co_hashtag = co_hashtag;
    }

    public HashtagModel() {
    }

    public void setCo_hashtag(String co_hashtag) {
        this.co_hashtag = co_hashtag;
    }

    public String getCo_hashtag() {
        return co_hashtag;
    }

    @Override
    public String toString() {
        return "HashtagModel{" +
                "co_hashtag='" + co_hashtag + '\'' +
                '}';
    }
}
