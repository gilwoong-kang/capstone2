package com.moayo.server.service.concrete;

import com.moayo.server.dao.Hashtag;
import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.HashtagModel;
import com.moayo.server.service.HashService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashtagServiceImpl implements HashService {
    @Autowired
    Hashtag hashtagDao;

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

    @Override
    public int insertHashtag(HashtagModel[] hashtagModels){
        try{
            if(hashtagModels.length != 0){
                int count = 0;
                for(HashtagModel hashtagModel : hashtagModels){
                    try{
                        long row = hashtagDao.insertHashtag(hashtagModel);
                        logger.trace("{} hashtag insert : {}",hashtagModel.getCo_hashtag(),row);
                        count++;
                    }catch (Exception e){
                        logger.error(hashtagModel.getCo_hashtag() + " : duplicate.");
                        continue;
                    }
                }
                logger.debug("hashtag insert success : {}",count);
                return count;
            }
            else{
                logger.debug("Hashtag is Empty : {}",hashtagModels.toString());
                return -1;
            }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR. : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
}
