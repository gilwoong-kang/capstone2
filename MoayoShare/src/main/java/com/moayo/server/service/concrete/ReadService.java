package com.moayo.server.service.concrete;

import com.moayo.server.dao.*;
import com.moayo.server.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean isDogamExist(int dogamId){
        try {
            DogamInfoModel dogamInfoModel = dogamListDao.getDogamById(dogamId);
            if(dogamInfoModel == null){
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
            dogamModel.setDogamInfoModel(dogamListDao.getDogamById(dogamId));
            logger.debug("{} dogam info read. : {}",dogamId,dogamModel.getDogamInfoModel().getCo_title());
            dogamModel.setCategoryModels(categoryDao.getCategoryByDogamId(dogamId));
            logger.debug("{} category read. : {}",dogamId,dogamModel.getCategoryModels().length);
            CategoryHashModel[] categoryHashModels = categoryHashDao.getByDogamId(dogamId);
            dogamModel.setCategoryHashModels(categoryHashModels);
            logger.debug("{} category - hash read. : {}",dogamId,categoryHashModels.length);
            CategoryPostModel[] categoryPostModels = categoryPostDao.getByDogamId(dogamId);
            dogamModel.setCategoryPostModels(categoryPostDao.getByDogamId(dogamId));
            logger.debug("{} category - post read. : {}",dogamId,categoryPostModels.length);
            PostModel[] postModels = new PostModel[categoryPostModels.length];
            for(int i = 0;i<postModels.length;i++){
                postModels[i] = postDao.getPost(categoryPostModels[i].getCo_postId());
            }
            dogamModel.setPostModels(postModels);
            logger.debug("{} post read. : {}",dogamId,postModels.length);
            HashtagModel[] hashtagModels = new HashtagModel[categoryHashModels.length];
            for(int i =0 ;i<hashtagModels.length;i++){
                hashtagModels[i] = new HashtagModel(categoryHashModels[i].getco_hashtag());
            }
            dogamModel.setHashtagModels(hashtagModels);
            logger.debug("{} hash read. : {}",dogamId,hashtagModels.length);

            if(dogamModel.getDogamInfoModel() != null) logger.info(dogamModel.getDogamInfoModel().toString());
            else logger.warn("dogam data NULL");
            return dogamModel;
        }catch (MyBatisSystemException dbConnectionException){
            logger.fatal("DB ERROR");
            logger.fatal(dbConnectionException.getMessage());
            return null;
        }
    }

    public List<DogamInfoModel> getDogamList() throws NullPointerException{
        try{
            List<DogamInfoModel> dogamInfoModels = dogamListDao.getAllDogam();
            logger.debug("All dogam Info read. : {}",dogamInfoModels.size());
            if(dogamInfoModels == null){
                throw new NullPointerException();
            }
            return dogamInfoModels;
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

    public List<DogamInfoModel> getDogamByWriterName(String writer){
        try{
            List<DogamInfoModel> dogamInfoModels = dogamListDao.getDogamByWriterName(writer);
            logger.debug("{} writer's dogam read : {}",writer,dogamInfoModels.size());
            return dogamInfoModels;
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
    }
    public List<DogamInfoModel> getDogamByKeyword(String keyword){
        try{
            List<DogamInfoModel> dogamInfoModels = dogamListDao.getDogamByDescriptionSearch(keyword);
            logger.debug("{} keyword dogam info : {}",keyword,dogamInfoModels.size());
            return dogamInfoModels;
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
    }
}
