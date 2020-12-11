package com.moayo.server.service.concrete;

import com.moayo.server.dao.Category;
import com.moayo.server.dao.CategoryHash;
import com.moayo.server.dao.Hashtag;
import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.model.DogamModel;
import com.moayo.server.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DogamServiceImpl implements DogamService {
    @Autowired
    DogamInfoService dogamInfoService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryHashService categoryHashService;
    @Autowired
    CategoryPostService categoryPostService;
    @Autowired
    PostService postService;
    @Autowired
    HashService hashService;

    private Logger logger;

    public DogamServiceImpl(){
        this.logger = LogManager.getLogger();
    }
    @Override
    @Transactional
    public DogamModel getDogam(int dogamId) {
        try{
            CategoryPostModel[] categoryPostModels = categoryPostService.getCategoryPost(dogamId);
            CategoryHashModel[] categoryHashModels = categoryHashService.getCategoryHash(dogamId);
            DogamModel dogamModel  = new DogamModel(dogamInfoService.getDogamInfoModel(dogamId),
                    categoryService.getCategory(dogamId),
                    postService.getPost(dogamId,categoryPostModels),
                    hashService.getHashtag(dogamId,categoryHashModels),
                            categoryPostModels,
                            categoryHashModels);
         if(dogamModel.getDogamInfoModel() != null) logger.info(dogamModel.getDogamInfoModel().toString());
         else logger.warn("dogam data NULL");
            return dogamModel;
        }catch (MyBatisSystemException e){
            logger.fatal("Some DB Error. Roll back data.");
            return null;
        }
    }

    @Override
    @Transactional
    public void insertDogam(DogamModel dogamModel) {
        dogamInfoService.insertDogamInfo(dogamModel.getDogamInfoModel());
        categoryService.insertCategory(dogamModel.getCategoryModels(),dogamModel.getDogamInfoModel()
                ,dogamModel.getCategoryPostModels(),dogamModel.getCategoryHashModels());
        postService.insertPost(dogamModel.getPostModels(),dogamModel.getCategoryPostModels());
        hashService.insertHashtag(dogamModel.getHashtagModels());
        categoryPostService.insertCategoryPost(dogamModel.getCategoryPostModels());
        categoryHashService.insertCategoryHash(dogamModel.getCategoryHashModels());
        logger.info("Insert Success {} : {}", dogamModel.getDogamInfoModel().getCo_dogamId(), dogamModel.getDogamInfoModel().getCo_title());
    }
}
