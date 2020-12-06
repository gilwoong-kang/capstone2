package com.moayo.server.dao;

import com.moayo.server.model.DogamInfoModel;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Dogam {

    long insertDogam(DogamInfoModel book);
    long updateDogam(DogamInfoModel book);
    long deleteDogamByModel(DogamInfoModel book);
    void deleteDogamById(int id);
    DogamInfoModel getDogamById(int id);
    List<DogamInfoModel> getAllDogam();
    List<DogamInfoModel> getDogamByWriterName(String writer);
    List<DogamInfoModel> getDogamByDescriptionSearch(String description);
    int like(int dogamId);
    int disLike(int dogamId);
}
