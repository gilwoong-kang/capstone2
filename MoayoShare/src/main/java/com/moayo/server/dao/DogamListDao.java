package com.moayo.server.dao;

import com.moayo.server.model.DogamListModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * MyBatis Mapper와 연결하는 interface.
 *
 * @author gilwoongkang
 */
@Repository
public interface DogamListDao {
    /**
     * 도감 정보를 insert한다.
     * @param book insert하고자 하는 도감의 정보가 담긴 model.
     * @see DogamListModel
     * @return insert row.
     */
    long insertDogam(DogamListModel book);

    /**
     * 도감 정보를 update 한다.
     * @param book update하고자 하는 도감의 정보가 담긴 model.
     * @return update row.
     */
    long updateDogam(DogamListModel book);

    /**
     * 도감 정보를 model값 기준으로 삭제한다.
     * @param book 삭제하고자 하는 도감의 정보가 담긴 model.
     * @return delete row.
     */
    long deleteDogamByModel(DogamListModel book);

    /**
     * 도감 정보를 도감 id기준으로 삭제한다.
     * @param id 삭제하고자 하는 도감의 id
     */
    void deleteDogamById(int id);

    /**
     * 도감 정보를 id값 기준으로 가져온다.
     * @param id 가져오고자 하는 도감의 id값
     * @return 가져온 도감의 model.
     */
    DogamListModel getDogamById(int id);

    /**
     * 모든 도감 정보를 가져온다.
     * @return 모든 도감의 model을 List에 담아 return.
     */
    List<DogamListModel> getAllDogam();

    /**
     * 특정 도감 정보를 작성자 기준으로 가져온다.
     * @param writer 작성자 nickname.
     * @return 도감 model이 담긴 List.
     */
    List<DogamListModel> getDogamByWriterName(String writer);

    /**
     * 설명란의 설명중 특정 키워드가 포함된 도감을 가져온다.
     * @param description 찾고자 하는 키워드.
     * @return 도감 model이 담긴 List.
     */
    List<DogamListModel> getDogamByDescriptionSearch(String description);

    /**
     * 특정 도감의 like를 1 올린다.
     * @param dogamId 좋아요 수치를 올리고자 하는 도감의 id.
     * @return like row.
     */
    int like(int dogamId);

    /**
     * 특정 도감의 like를 1내린다.
     * @param dogamId 좋아요 수치를 내리고자 하는 도감의 id.
     * @return dislike row.
     */
    int disLike(int dogamId);
}
