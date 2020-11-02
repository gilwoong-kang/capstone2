package com.moayo.server.service.concrete;

import com.moayo.server.dao.*;
import com.moayo.server.model.*;
import com.moayo.server.service.InsertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Exception.DogamInsertException;
import util.Exception.NoDogamIdException;

import java.util.*;

@Service
public class InsertServiceImpl implements InsertService {

    @Autowired
    DogamListDao dogamListDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    PostDao postDao;
    @Autowired
    HashtagDao hashtagDao;
    @Autowired
    CategoryPostDao categoryPostDao;
    @Autowired
    CategoryHashDao categoryHashDao;

    private Logger logger;

    public InsertServiceImpl() {
        logger = LogManager.getLogger();
    }

    public void insertData(DogamModel dogamModel){
        DogamListModel dogamListModel = dogamModel.getDogamListModel();
        CategoryModel[] categoryModels = dogamModel.getCategoryModels();
        HashtagModel[] hashtagModels = dogamModel.getHashtagModels();
        PostModel[] postModels = dogamModel.getPostModels();
        CategoryHashModel[] categoryHashModels = dogamModel.getCategoryHashModels();
        CategoryPostModel[] categoryPostModels = dogamModel.getCategoryPostModels();

        try{
            if(dogamListDao.insertDogam(dogamListModel) == 0) throw new DogamInsertException("DogamList Insert Error");
            Map<Integer,CategoryModel> categoryModelMap = categoryInsert(categoryModels,dogamListModel,categoryPostModels,categoryHashModels);
            postInsert(postModels,categoryPostModels);
            if(hashtagModels.length != 0)
                hashtagInsert(hashtagModels);
            if(categoryPostModels.length != 0)
                categoryPostDao.insertAll(categoryPostModels);
            if(categoryHashModels.length != 0)
                categoryHashDao.insertAll(categoryHashModels);
            logger.info("Insert Success {} : {}",dogamListModel.getCo_dogamId(),dogamListModel.getCo_title());
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        } catch (DogamInsertException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void like(int dogamId) throws NoDogamIdException{
        try{
            if(dogamListDao.like(dogamId) == 0){
                throw new NoDogamIdException(dogamId + " is NOT EXIST.");
            }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    @Override
    public void disLike(int dogamId) throws NoDogamIdException{
        try{
            if(dogamListDao.disLike(dogamId) == 0){
                throw new NoDogamIdException(dogamId + " is NOT EXIST.");
            }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    private void hashtagInsert(HashtagModel[] hashtagModels){
        for(HashtagModel hashtagModel : hashtagModels){
            try{
                hashtagDao.insertHashtag(hashtagModel);
            }catch (Exception e){
                logger.error(hashtagModel.getCo_hashtag() + " : duplicate.");
                continue;
            }
        }
    }

    private void postInsert(PostModel[] postModels,CategoryPostModel[] categoryPostModels){
        for(PostModel postModel : postModels){
            int origin = postModel.getCo_postId();
            postDao.insertPost(postModel);
            for(CategoryPostModel categoryPostModel : categoryPostModels){
                if(categoryPostModel.getCo_postId() == origin){
                    categoryPostModel.setCo_postId(postModel.getCo_postId());
                }
            }
        }
    }
    private Map<Integer,CategoryModel> categoryInsert(CategoryModel[] categoryModels,DogamListModel dogamListModel,
                                                     CategoryPostModel[] categoryPostModels, CategoryHashModel[] categoryHashModels){
        Map<Integer,CategoryModel> categoryModelMap = new HashMap<Integer,CategoryModel>();
        Map<Integer,List<CategoryModel>> levelLabelCategory = labelCategory(categoryModels);
        logger.debug("Inserting category data");
        for(int i = 1; i< levelLabelCategory.keySet().size()+1;i++){
            for(CategoryModel categoryModel : levelLabelCategory.get(i)){
                int origin = 0;
                try{
                    categoryModel.setCo_dogamId(dogamListModel.getCo_dogamId());
                    origin = categoryModel.getCo_categoryId();
                    // 루트일때
                    if(categoryModel.getCo_categoryId() == categoryModel.getCo_parentCategoryId()){
                        categoryDao.foreignKeyOFF();
                        categoryDao.insertCategory(categoryModel);
                        categoryDao.foreignKeyON();
                        categoryModel.setCo_parentCategoryId(categoryModel.getCo_categoryId());
                        categoryDao.updateCategory(categoryModel);
                    }
                    // 일반적 경우
                    else{
                        categoryModel.setCo_parentCategoryId(categoryModelMap.get(categoryModel.getCo_parentCategoryId()).getCo_categoryId());
                        categoryDao.insertCategory(categoryModel);
                    }
                    categoryModelMap.put(origin,categoryModel);
                }catch (Exception e){
                    logger.error("Category insert SQL ERROR : " + this.getClass().getName());
                    logger.error(e.getMessage());
                }
                logger.debug("Inserting category - post data");
                for(CategoryPostModel categoryPostModel : categoryPostModels){
                    if(categoryPostModel.getCo_categoryId() == origin){
                        categoryPostModel.setCo_categoryId(categoryModel.getCo_categoryId());
                        categoryPostModel.setCo_dogamId(categoryModel.getCo_dogamId());
                    }
                }
                logger.debug("Inserting category - hash data");
                for(CategoryHashModel categoryHashModel : categoryHashModels){
                    if(categoryHashModel.getco_categoryId() == origin){
                        categoryHashModel.setco_dogamId(categoryModel.getCo_dogamId());
                        categoryHashModel.setco_categoryId(categoryModel.getCo_categoryId());
                    }
                }
            }
        }
        return categoryModelMap;
    }
    private Map<Integer, List<CategoryModel>>  labelCategory(CategoryModel[] categoryModels){
        Map<Integer,List<CategoryModel>> labelCategory = new HashMap<Integer, List<CategoryModel>>();
        for(CategoryModel categoryModel : categoryModels){
            if(!labelCategory.containsKey(categoryModel.getCo_level())){
                List<CategoryModel> list = new ArrayList<CategoryModel>();
                list.add(categoryModel);
                labelCategory.put(categoryModel.getCo_level(),list);
            }else{
                labelCategory.get(categoryModel.getCo_level()).add(categoryModel);
            }
        }
        logger.debug("Relabeling Category data");
        return labelCategory;
    }
}
