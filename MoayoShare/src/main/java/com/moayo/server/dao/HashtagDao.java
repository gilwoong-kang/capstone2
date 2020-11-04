package com.moayo.server.dao;

import com.moayo.server.model.HashtagModel;

import java.util.List;

/**
 * MyBatis Mapper와 연결하는 interface.
 *
 * @author gilwoongkang
 */
public interface HashtagDao {
    /**
     * 하나의 해시태그 정보를 insert한다.
     * @param hashtagModel 입력하고자 하는 해시태그 정보.
     * @see HashtagModel
     * @return insert row.
     */
    long insertHashtag(HashtagModel hashtagModel);

    /**
     * 여러개의 해시태그 정보를 insert한다.
     * @param hashtagModels 입력하고자 하는 해시태그 정보의 Array.
     */
    void insertAll(HashtagModel[] hashtagModels);

    /**
     * 특정 해시태그 정보를 삭제한다.
     * @param hashtagModel 삭제하고자 하는 해시태그 정보.
     * @return delete row.
     */
    long deleteHashtag(HashtagModel hashtagModel);

    /**
     * 특정 해시태그 정보를 삭제한다.
     * @param hashtag 삭제하고자 하는 해시태그의 String.
     * @return delete row.
     */
    long deleteHashtagByString(String hashtag);

    /**
     * 모든 해시태그 정보를 가져온다.
     * @return 가지고 있는 모든 해시태그 정보 model의 List
     */
    List<HashtagModel> getAllHashtag();

    /**
     * 특정 해시태그를 update한다.
     * @param hashtagModel update하고자 하는 hashtag정보
     * @return update row.
     */
    long updateHashtag(HashtagModel hashtagModel);
}
