package com.moayo.server.dao;

import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.CategoryPostModel;

import java.util.List;

/**
 * MyBatis Mapper와 연결하는 interface.
 * 해당 인터페이스와 연결된 데이터베이스의 경우 cascade이기 때문에 타 테이블에서 row삭제시 해당 테이블의 값도 자동으로 삭제됨에 주의.
 *
 * @author gilwoongkang
 */
public interface CategoryHashDao {
    /**
     * 하나의 row를 insert한다.
     * @param categoryHashModel 카테고리 - 해시태그 정보가 담긴 model.
     * @see CategoryHashModel
     * @return insert row.
     */
    long insertCategoryHashtag(CategoryHashModel categoryHashModel);

    /**
     * 여러 rows를 insert 한다.
     * @param categoryHashModels 카테고리 - 해시태그 정보가 담긴 model들의 Array.
     * @return insert rows.
     */
    long insertAll(CategoryHashModel[] categoryHashModels);

    /**
     * Dogam에 포함된 모든 카테고리 - 해시태그 정보를 삭제한다.
     * @param dogamId   삭제하려는 도감의 id.
     * @return  삭제한 rows count.
     */
    long deleteByDogamId(int dogamId);

    /**
     * 특정 카테고리 - 해시태그 정보를 삭제한다.
     * @param categoryId 삭제하려는 categoryID
     * @param dogamId   삭제하려는 dogamID
     * @return 삭제한 rows count
     */
    long deleteByCategoryId(int categoryId,int dogamId);

    /**
     * 특정 해시태그 정보를 포함한 모든 rows 삭제.
     * @param hashtag 삭제하려는 해시태그정보
     * @return 삭제한 rows count
     */
    long deleteByHashtag(String hashtag);

    /**
     * 특정 도감과 관련된 모든 카테고리 - 해시태그 정보를 가져온다.
     * @param dogamId   찾으려는 카테고리 - 해시태그 정보의 도감 id
     * @return  찾은 카테고리 - 해시태그 정보의 Array
     */
    CategoryHashModel[] getByDogamId(int dogamId);

    /**
     * 특정 카테고리 - 해시태그 정보를 가져온다.
     * @param categoryId 찾으려는 카테고리id
     * @param dogamId 찾으려는 도감 id
     * @return  찾은 카테고리 - 해시태그 정보
     */
    CategoryHashModel getByCategoryId(int categoryId,int dogamId);

    /**
     * 특정 카테고리 - 해시태그 정보를 가져온다.
     * @param hashtag 해시태그 정보를 가지고 정보를 찾는다.
     * @return 찾은 카테고리 - 해시태그 정보.
     */
    CategoryHashModel getByHashtag(String hashtag);
}
