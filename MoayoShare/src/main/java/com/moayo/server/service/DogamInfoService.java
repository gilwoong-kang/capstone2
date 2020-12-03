package com.moayo.server.service;

import com.moayo.server.model.DogamInfoModel;
import util.Exception.NoDogamIdException;

import java.util.List;

public interface DogamInfoService {
    public boolean isDogamExist(int dogamId);
    public DogamInfoModel getDogamInfoModel(int dogamId);
    public List<DogamInfoModel> getAllDogamInfo();
    public void deleteDogamInfo(int dogamId);
    public List<DogamInfoModel> getDogamByWriterName(String writer);
    public List<DogamInfoModel> getDogamByKeyword(String keyword);

    int insertDogamInfo(DogamInfoModel dogamInfoModel);

    void like(int dogamId) throws NoDogamIdException;

    void disLike(int dogamId) throws NoDogamIdException;
}
