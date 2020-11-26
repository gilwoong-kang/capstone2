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
public class DataReadService {
    @Autowired
    DogamList dogamList;
    @Autowired
    Category category;
    @Autowired
    CategoryHash categoryHash;
    @Autowired
    CategoryPost categoryPost;
    @Autowired
    Post post;

    private Logger logger;

    public DataReadService() {
        this.logger = LogManager.getLogger();
    }

    public boolean isDogamExist(int dogamId){
        try {
            DogamInfoModel dogamInfoModel = dogamList.getDogamById(dogamId);
            if(dogamInfoModel == null){
                logger.warn("{} dogam is NOT EXIST.",dogamId);
                return false;
            }
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR");
            logger.fatal(e.getMessage());
            return false;
        }
        return true;
    }

    public DogamModel getDogam(int dogamId){
        DogamModel dogamModel = new DogamModel();
        try{
            dogamModel.setDogamInfoModel(dogamList.getDogamById(dogamId));
            logger.debug("{} dogam info read. : {}",dogamId,dogamModel.getDogamInfoModel().getCo_title());
            dogamModel.setCategoryModels(category.getCategoryByDogamId(dogamId));
            logger.debug("{} category read. : {}",dogamId,dogamModel.getCategoryModels().length);
            CategoryHashModel[] categoryHashModels = categoryHash.getByDogamId(dogamId);
            dogamModel.setCategoryHashModels(categoryHashModels);
            logger.debug("{} category - hash read. : {}",dogamId,categoryHashModels.length);
            CategoryPostModel[] categoryPostModels = categoryPost.getByDogamId(dogamId);
            dogamModel.setCategoryPostModels(categoryPost.getByDogamId(dogamId));
            logger.debug("{} category - post read. : {}",dogamId,categoryPostModels.length);
            PostModel[] postModels = new PostModel[categoryPostModels.length];
            for(int i = 0;i<postModels.length;i++){
                postModels[i] = post.getPost(categoryPostModels[i].getCo_postId());
            }
            dogamModel.setPostModels(postModels);
            logger.debug("{} post read. : {}",dogamId,postModels.length);
            HashtagModel[] hashtagModels = new HashtagModel[categoryHashModels.length];
            for(int i =0 ;i<hashtagModels.length;i++){
                hashtagModels[i] = new HashtagModel(categoryHashModels[i].getco_hashtag());
            }
            dogamModel.setHashtagModels(hashtagModels);
            logger.debug("{} hash read. : {}",dogamId,hashtagModels.length);
        }catch (MyBatisSystemException dbConnectionException){
            logger.fatal("DB ERROR");
            logger.fatal(dbConnectionException.getMessage());
            return null;
        }
        if(dogamModel.getDogamInfoModel() != null) logger.info(dogamModel.getDogamInfoModel().toString());
        else logger.warn("dogam data NULL");
        return dogamModel;
    }

    public List<DogamInfoModel> getDogamList() throws NullPointerException{
        List<DogamInfoModel> dogamInfoModels;
        try{
            dogamInfoModels = dogamList.getAllDogam();
            logger.debug("All dogam Info read. : {}",dogamInfoModels.size());
            if(dogamInfoModels == null){ throw new NullPointerException(); }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
        return dogamInfoModels;
    }

    public void deleteDogam(int dogamId){
        try{
            dogamList.deleteDogamById(dogamId);
            logger.debug("Deleting Dogam success.");
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    public List<DogamInfoModel> getDogamByWriterName(String writer){
        List<DogamInfoModel> dogamInfoModels;
        try{
            dogamInfoModels = dogamList.getDogamByWriterName(writer);
            logger.debug("{} writer's dogam read : {}",writer,dogamInfoModels.size());
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
        return dogamInfoModels;
    }
    public List<DogamInfoModel> getDogamByKeyword(String keyword){
        List<DogamInfoModel> dogamInfoModels;
        try{
            dogamInfoModels= dogamList.getDogamByDescriptionSearch(keyword);
            logger.debug("{} keyword dogam info : {}",keyword,dogamInfoModels.size());
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
        return dogamInfoModels;
    }
}
