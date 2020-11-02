package com.moayo.server.service.concrete;

import com.moayo.server.dao.*;
import com.moayo.server.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Exception.getDogamDBException;

import java.util.List;

@Service
public class ReadService {
    @Autowired
    DogamListDao dogamListDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryHashDao categoryHashDao;
    @Autowired
    CategoryPostDao categoryPostDao;
    @Autowired
    PostDao postDao;

    private Logger logger;

    public ReadService() {
        this.logger = LogManager.getLogger();
    }

    public boolean isDogam(int dogamId){
        try {
            DogamListModel dogamListModel = dogamListDao.getDogamById(dogamId);
            if(dogamListModel == null){
                logger.warn("{} dogam is NOT EXIST.",dogamId);
                return false;
            }
            return true;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR");
            logger.fatal(e.getMessage());
            return false;
        }
    }

    public DogamModel getDogam(int dogamId){
        DogamModel dogamModel = new DogamModel();
        try{
            dogamModel.setDogamListModel(dogamListDao.getDogamById(dogamId));
            dogamModel.setCategoryModels(categoryDao.getCategoryByDogamId(dogamId));

            CategoryHashModel[] categoryHashModels = categoryHashDao.getByDogamId(dogamId);
            dogamModel.setCategoryHashModels(categoryHashModels);

            CategoryPostModel[] categoryPostModels = categoryPostDao.getByDogamId(dogamId);
            dogamModel.setCategoryPostModels(categoryPostDao.getByDogamId(dogamId));

            PostModel[] postModels = new PostModel[categoryPostModels.length];
            for(int i = 0;i<postModels.length;i++){
                postModels[i] = postDao.getPost(categoryPostModels[i].getCo_postId());
            }
            dogamModel.setPostModels(postModels);

            HashtagModel[] hashtagModels = new HashtagModel[categoryHashModels.length];
            for(int i =0 ;i<hashtagModels.length;i++){
                hashtagModels[i] = new HashtagModel(categoryHashModels[i].getco_hashtag());
            }
            dogamModel.setHashtagModels(hashtagModels);
            if(dogamModel.getDogamListModel() != null) logger.info(dogamModel.getDogamListModel().toString());
            else logger.warn("dogam data NULL");
            return dogamModel;
        }catch (MyBatisSystemException dbConnectionException){
            logger.fatal("DB ERROR");
            logger.fatal(dbConnectionException.getMessage());
            return null;
        }
    }

    public List<DogamListModel> getDogamList() throws NullPointerException{
        try{
            List<DogamListModel> dogamListModels = dogamListDao.getAllDogam();
            if(dogamListModels == null){
                throw new NullPointerException();
            }
            return dogamListModels;
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
    }

    public void deleteDogam(int dogamId){
        try{
            dogamListDao.deleteDogamById(dogamId);
            logger.debug("Deleting Dogam success.");
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return;
        }
    }

    public List<DogamListModel> getDogamByWriterName(String writer){
        try{
            return dogamListDao.getDogamByWriterName(writer);
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
    }
    public List<DogamListModel> getDogamByKeyword(String keyword){
        try{
            return dogamListDao.getDogamByDescriptionSearch(keyword);
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
    }
}
