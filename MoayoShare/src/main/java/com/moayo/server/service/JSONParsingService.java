package com.moayo.server.service;

import com.moayo.server.model.DogamModel;

public interface JSONParsingService {
    public void insertData(DogamModel dogamModel);
    public void like(int dogamId);
    public void disLike(int dogamId);
}
