package com.moayo.server.service;

import com.moayo.server.model.DogamInfoModel;

import java.util.List;

public interface DogamInfoService {
    public boolean isDogamExist(int dogamId);
    public DogamInfoModel getDogamInfoModel(int dogamId);
    public List<DogamInfoModel> getAllDogamInfo();
    public int deleteDogamInfo(int dogamId);
    public List<DogamInfoModel> getDogamByWriterName(String writer);
    public List<DogamInfoModel> getDogamByKeyword(String keyword);
}
