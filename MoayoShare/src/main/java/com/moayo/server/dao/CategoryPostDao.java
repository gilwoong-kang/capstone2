package com.moayo.server.dao;

import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.CategoryPostModel;

/**
 * MyBatis Mapper와 연결하는 interface.
 * 해당 인터페이스와 연결된 데이터베이스의 경우 cascade이기 때문에 타 테이블에서 row삭제시 해당 테이블의 값도 자동으로 삭제됨에 주의.
 *
 * @author gilwoongkang
 */
public interface CategoryPostDao {
    /**
     * 하나의 카테고리 - 게시물 정보를 insert 한다.
     * @param categoryPostModel 카테고리 - 게시물 정보가 담기는 model.
     * @see CategoryPostModel
     * @return insert row.
     */
    long insertCategoryPost(CategoryPostModel categoryPostModel);

    /**
     * 여러개의 카테고리 - 게시물 정보를 insert 한다.
     * @param categoryPostModels 카테고리 - 게시물 정보가 담기는 model의 Array.
     */
    long insertAll(CategoryPostModel[] categoryPostModels);

    /**
     * 특정 도감과 관련된 카테고리 - 게시물 정보를 삭제한다.
     * @param dogamId 도감 id.
     * @return 삭제한 rows.
     */
    long deleteByDogamId(int dogamId);

    /**
     * 특정 카테고리 - 게시물 정보를 카테고리 기준으로 삭제한다.
     * @param categoryId 삭제하고자하는 카테고리 id
     * @param dogamId 삭제 하고자 하는 도감 id
     * @return 삭제한 rows.
     */
    long deleteByCategoryId(int categoryId,int dogamId);

    /**
     * 특정 카테고리 - 게시물 정보를 게시물 기준으로 삭제한다.
     * @param postId 삭제하고자 하는 게시물 Id
     * @return 삭제한 rows.
     */
    long deleteByPostId(String postId);

    /**
     * 특정 카테고리 - 게시물 정보를 도감 id 기준으로 가져온다.
     * @param dogamId 가져오고자 하는 정보의 도감 id
     * @return 가져온 model을 Array로 리턴한다.
     */
    CategoryPostModel[] getByDogamId(int dogamId);

    /**
     * 특정 카테고리 - 게시물 정보를 키테고리 id 기준으로 가져온다.
     * @param categoryId 가져오고자 하는 카테고리 id.
     * @param dogamId 가져오고자 하는 카테고리의 도감 id.
     * @return 가져온 model.
     */
    CategoryPostModel getByCategoryId(int categoryId,int dogamId);

    /**
     * 특정 카테고리 - 게시물 정보를 게시물 Id 기준으로 가져온다.
     * @param postId 가져오고자 하는 게시물 id
     * @return 가져온 model.
     */
    CategoryPostModel getByPostId(int postId);
}
