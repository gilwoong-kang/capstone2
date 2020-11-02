package com.moayo.server.service;

import com.moayo.server.model.DogamModel;
import util.Exception.NoDogamIdException;

public interface InsertService {
    public void insertData(DogamModel dogamModel);
    public void like(int dogamId) throws NoDogamIdException;
    public void disLike(int dogamId) throws NoDogamIdException;
}
