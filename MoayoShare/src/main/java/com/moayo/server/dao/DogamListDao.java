package com.moayo.server.dao;

import com.moayo.server.model.DogamListModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface DogamListDao {
    long insertDogam(DogamListModel book);
    long updateDogam(DogamListModel book);
    long deleteDogamByModel(DogamListModel book);
    void deleteDogamById(int id);
    DogamListModel getDogamById(int id);
    List<DogamListModel> getAllDogam();
    List<DogamListModel> getDogamByWriterName(String writer);
    List<DogamListModel> getDogamByDescriptionSearch(String description);
    void like(int dogamId);
    void disLike(int dogamId);
}
