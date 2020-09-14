package com.moayo.server.dao;

import com.moayo.server.model.HashtagModel;

import java.util.List;

public interface HashtagDao {
    long insertHashtag(HashtagModel hashtagModel);
    void insertAll(HashtagModel[] hashtagModels);
    long deleteHashtag(HashtagModel hashtagModel);
    long deleteHashtagByString(String hashtag);
    List<HashtagModel> getAllHashtag();
    long updateHashtag(HashtagModel hashtagModel);
}
