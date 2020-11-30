package com.moayo.server.service.concrete;

import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.HashtagModel;
import com.moayo.server.service.HashService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;

public class HashtagServiceImpl implements HashService {

    private Logger logger;

    public HashtagServiceImpl(){this.logger = LogManager.getLogger();}

    @Override
    public HashtagModel[] getHashtag(int dogamId, CategoryHashModel[] categoryHashModels) {
        try{
            HashtagModel[] hashtagModels = new HashtagModel[categoryHashModels.length];
            for(int i =0 ;i<hashtagModels.length;i++){
                hashtagModels[i] = new HashtagModel(categoryHashModels[i].getco_hashtag());
            }
            logger.debug("{} hash read. : {}",dogamId,hashtagModels.length);
            return hashtagModels;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
}
