package com.moayo.server.dao;

import com.moayo.server.model.CategoryModel;

/**
 * MyBatis Mapper와 연결하는 interface.
 *
 * @author gilwoongkang
 */

public interface CategoryDao {
    /**
     * 일반적 경우의 카테고리 insert.
     * @param categoryModel 카테고리에 대한 model.
     * @see CategoryModel
     * @return row값 반환.
     */
    long insertCategory(CategoryModel categoryModel);

    /**
     * 카테고리를 업데이트 한다.
     * @param categoryModel 카테고리에 대한 model.
     * @see CategoryModel
     * @return 업데이트된 row갯수 반환.
     */
    long updateCategory(CategoryModel categoryModel);

    /**
     * 카테고리를 제거한다. 모델 내부 카테고리 id를 가지고 제거한다.
     * @param categoryModel 카테고리에 대한 model.
     * @return 삭제된 row 갯수 반환.
     */
    long deleteCategory(CategoryModel categoryModel);

    /**
     * 카테고리를 제거한다. dogam id와 category id 모두 일치하는 카테고리를 제거한다.
     * @param id 카테고리에 대한 id
     * @param dogamId 도감에 대한 id
     * @return 삭제된 row 갯수 반환
     */
    long deleteCategoryById(int id,int dogamId);

    /**
     * 카테고리를 제거한다. dogam id를 가지고 해당 도감과 관련된 모든 카테고리를 제거한다.
     * @param dogamId 도감에 대한 id
     * @return 삭제된 row 갯수 반환.
     */
    long deleteCategoryByDogamId(int dogamId);

    /**
     * 카테고리를 Id로 검색해서 가져온다. dogam id와 category의 id를 이용한다.
     * @param id 카테고리 id
     * @param dogamId 도감의 id
     * @return 카테고리 정보를 model에 매핑하여 리턴한다.
     * @see CategoryModel
     */
    CategoryModel getCategoryById(int id,int dogamId);

    /**
     * 도감과 관련된 모든 카테고리를 가져온다. dogam id를 입력해 관련 도감을 검색한다.
     * @param dogamId 도감 Id.
     * @return 카테고리들의 Array.
     */
    CategoryModel[] getCategoryByDogamId(int dogamId);

    /**
     * 루트 카테고리 insert시 외래키 문제 해결을 위한 메소드.
     * foreignkey의 영향을 받도록 변경한다.
     */
    void foreignKeyON();

    /**
     * 루트 카테고리 insert시 외래키 문제 해결을 위한 메소드.
     * foreignKey의 영향을 받지 않도록 변경한다.
     */
    void foreignKeyOFF();
}
