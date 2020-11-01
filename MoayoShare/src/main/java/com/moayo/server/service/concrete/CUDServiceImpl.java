package com.moayo.server.service.concrete;

import com.moayo.server.dao.*;
import com.moayo.server.model.*;
import com.moayo.server.service.CUDService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Exception.NoDogamIdException;

import java.util.*;

@Service
public class CUDServiceImpl implements CUDService {

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

    public CUDServiceImpl() {
        logger = LogManager.getLogger();
    }

    public void insertData(DogamModel dogamModel){
        DogamListModel dogamListModel = dogamModel.getDogamListModel();
        CategoryModel[] categoryModels = dogamModel.getCategoryModels();
        HashtagModel[] hashtagModels = dogamModel.getHashtagModels();
        PostModel[] postModels = dogamModel.getPostModels();
        CategoryHashModel[] categoryHashModels = dogamModel.getCategoryHashModels();
        CategoryPostModel[] categoryPostModels = dogamModel.getCategoryPostModels();


        dogamListDao.insertDogam(dogamListModel);
        Map<Integer,CategoryModel> categoryModelMap = categoryInsert(categoryModels,dogamListModel,categoryPostModels,categoryHashModels);
        postInsert(postModels,categoryPostModels);
        if(hashtagModels.length != 0)
            hashtagInsert(hashtagModels);
        if(categoryPostModels.length != 0)
            categoryPostDao.insertAll(categoryPostModels);
        if(categoryHashModels.length != 0)
            categoryHashDao.insertAll(categoryHashModels);
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
        for(int i = 1; i< levelLabelCategory.keySet().size()+1;i++){
            for(CategoryModel categoryModel : levelLabelCategory.get(i)){
                int origin = 0;
                try{
                    // 루트일때
                    if(categoryModel.getCo_categoryId() == categoryModel.getCo_parentCategoryId()){
                        categoryModel.setCo_dogamId(dogamListModel.getCo_dogamId());
                        origin = categoryModel.getCo_categoryId();
                        categoryDao.foreignKeyOFF();
                        categoryDao.insertCategory(categoryModel);
                        categoryDao.foreignKeyON();
                        categoryModel.setCo_parentCategoryId(categoryModel.getCo_categoryId());
                        categoryDao.updateCategory(categoryModel);
                        categoryModelMap.put(origin,categoryModel);
                    }else{
                        // 일반적 경우
                        categoryModel.setCo_dogamId(dogamListModel.getCo_dogamId());
                        origin = categoryModel.getCo_categoryId();
                        categoryModel.setCo_parentCategoryId(categoryModelMap.get(categoryModel.getCo_parentCategoryId()).getCo_categoryId());
                        categoryDao.insertCategory(categoryModel);
                        categoryModelMap.put(origin,categoryModel);
                    }
                }catch (Exception e){
                    logger.error("category insert SQL ERROR : " + this.getClass().getName());
                }

                for(CategoryPostModel categoryPostModel : categoryPostModels){
                    if(categoryPostModel.getCo_categoryId() == origin){
                        categoryPostModel.setCo_categoryId(categoryModel.getCo_categoryId());
                        categoryPostModel.setCo_dogamId(categoryModel.getCo_dogamId());
                    }
                }
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
        return labelCategory;
    }
}
