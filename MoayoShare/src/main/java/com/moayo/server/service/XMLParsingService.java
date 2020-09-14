package com.moayo.server.service;

import com.moayo.server.model.DogamListModel;
import org.w3c.dom.Document;

public interface XMLParsingService {
    public DogamListModel insertData(Document doc);
}
