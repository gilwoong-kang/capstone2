package com.moayo.server.service;

import com.moayo.server.model.DogamModel;

public interface DogamService {
    public DogamModel getDogam(int dogamId);
    public void insertDogam(DogamModel dogamModel);
}
