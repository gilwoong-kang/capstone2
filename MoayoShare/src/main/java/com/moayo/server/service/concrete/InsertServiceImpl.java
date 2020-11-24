package com.moayo.server.service.concrete;

import com.moayo.server.dao.*;
import com.moayo.server.model.*;
import com.moayo.server.service.InsertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    /**
     * 도감 정보를 서버의 DB 값에 맞추어 변경한 뒤 insert한다.
     * 주로 ID값이 모바일 환경과 다르므로 ID값의 재 라벨링을 수행한다.
     *
     * @param dogamModel 도감 전체 정보
     * @see DogamModel
     */
    public void insertData(DogamModel dogamModel){
        // 각 테이블별 모델값으로 추출.
        DogamInfoModel dogamInfoModel = dogamModel.getDogamInfoModel();
        CategoryModel[] categoryModels = dogamModel.getCategoryModels();
        HashtagModel[] hashtagModels = dogamModel.getHashtagModels();
        PostModel[] postModels = dogamModel.getPostModels();
        CategoryHashModel[] categoryHashModels = dogamModel.getCategoryHashModels();
        CategoryPostModel[] categoryPostModels = dogamModel.getCategoryPostModels();

        try{
            if(dogamListDao.insertDogam(dogamInfoModel) == 0) logger.error("{} : DogamList Insert Error"); // category 재 라벨링 수행 이후 insert
            categoryInsert(categoryModels, dogamInfoModel,categoryPostModels,categoryHashModels);

            int postrows = postInsert(postModels,categoryPostModels);
            logger.debug("{} post insert success : {}",dogamInfoModel,postrows);
            if(hashtagModels.length != 0){
                int hashtagRows = hashtagInsert(hashtagModels);
                logger.debug("{} hashtag insert success : {}",dogamInfoModel,hashtagRows);
            }
            if(categoryPostModels.length != 0) {
                long rows = categoryPostDao.insertAll(categoryPostModels);
                logger.debug("{} category post insert success : {}",dogamInfoModel,rows);
            }
            if(categoryHashModels.length != 0){
                long rows = categoryHashDao.insertAll(categoryHashModels);
                logger.debug("{} category hash insert success : {}",dogamInfoModel,rows);
            }
            logger.info("Insert Success {} : {}", dogamInfoModel.getCo_dogamId(), dogamInfoModel.getCo_title());
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    @Override
    public void like(int dogamId) throws NoDogamIdException{
        try{
            if(dogamListDao.like(dogamId) == 0){
                throw new NoDogamIdException(dogamId + " is NOT EXIST.");
            }
            logger.debug("{} like success.",dogamId);
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
            logger.debug("{} dislike success.",dogamId);
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    private int hashtagInsert(HashtagModel[] hashtagModels){
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
        return count;
    }

    /**
     * @// TODO: 2020/11/11 exception add. 해야함.
     */
    private int postInsert(PostModel[] postModels,CategoryPostModel[] categoryPostModels){
        int count = 0;
        for(PostModel postModel : postModels){
            int origin = postModel.getCo_postId();
            long rows = postDao.insertPost(postModel);
            logger.trace("{} post Insert : {}",postModel.getCo_postId(),rows);
            count++;
            for(CategoryPostModel categoryPostModel : categoryPostModels){
                if(categoryPostModel.getCo_postId() == origin){
                    categoryPostModel.setCo_postId(postModel.getCo_postId());
                }
            }
        }
        return count;
    }

    /**
     * 카테고리에 대해 재 라벨링 수행 이후 insert 수행한다.
     * @return <재라벨링된 id,카테고리정보>
     */
    private Map<Integer,CategoryModel> categoryInsert(CategoryModel[] categoryModels, DogamInfoModel dogamInfoModel,
                                                      CategoryPostModel[] categoryPostModels, CategoryHashModel[] categoryHashModels){
        Map<Integer,CategoryModel> categoryModelMap = new HashMap<Integer,CategoryModel>();
        logger.debug("{} category data relabeling",dogamInfoModel);
        Map<Integer,List<CategoryModel>> levelLabelCategory = labelCategory(categoryModels); // 재 라벨링.
        logger.debug("{} Inserting category data",dogamInfoModel);

        for(int i = 1; i< levelLabelCategory.keySet().size()+1;i++){
            for(CategoryModel categoryModel : levelLabelCategory.get(i)){
                int origin = 0;
                try{
                    categoryModel.setCo_dogamId(dogamInfoModel.getCo_dogamId());
                    origin = categoryModel.getCo_categoryId();
                    // 루트일때 - 외래키를 해제하여 값을 넣어야 함.
                    if(categoryModel.getCo_categoryId() == categoryModel.getCo_parentCategoryId()){
                        categoryDao.foreignKeyOFF();
                        logger.trace("foreignKey Off");
                        long rows = categoryDao.insertCategory(categoryModel);
                        categoryDao.foreignKeyON();
                        logger.trace("foreignKey On");
                        categoryModel.setCo_parentCategoryId(categoryModel.getCo_categoryId());
                        categoryDao.updateCategory(categoryModel);
                        logger.debug("{} Dogam root insert rows : {} ",dogamInfoModel,rows);
                    }
                    // 일반적 경우
                    else{
                        categoryModel.setCo_parentCategoryId(categoryModelMap.get(categoryModel.getCo_parentCategoryId()).getCo_categoryId());
                        categoryDao.insertCategory(categoryModel);
                        logger.debug("{} category Insert : {}",dogamInfoModel,categoryModel.getCo_categoryId());
                    }
                    categoryModelMap.put(origin,categoryModel);
                }catch (Exception e){
                    logger.error("Category insert SQL ERROR : " + this.getClass().getName());
                    logger.error(e.getMessage());
                }
                for(CategoryPostModel categoryPostModel : categoryPostModels){
                    if(categoryPostModel.getCo_categoryId() == origin){
                        categoryPostModel.setCo_categoryId(categoryModel.getCo_categoryId());
                        categoryPostModel.setCo_dogamId(categoryModel.getCo_dogamId());
                        logger.trace("{} category - post data labeling {} ",dogamInfoModel,categoryPostModel.getCo_postId());
                    }
                }
                for(CategoryHashModel categoryHashModel : categoryHashModels){
                    if(categoryHashModel.getco_categoryId() == origin){
                        categoryHashModel.setco_dogamId(categoryModel.getCo_dogamId());
                        categoryHashModel.setco_categoryId(categoryModel.getCo_categoryId());
                        logger.trace("{} category - hash data labeling {} ",dogamInfoModel,categoryHashModel.getco_hashtag());
                    }
                }
            }
        }
        logger.info("{} dogam insert {} cateogries.",dogamInfoModel,categoryModelMap.size());
        return categoryModelMap;
    }

    /**
     * 서버 DB값에 맞는 ID 재 라벨링.
     * @param categoryModels insert 하려는 category의 정보들
     * @return 재 라벨링된 카테고리 map.
     */
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
        logger.debug("Relabeling Category data success : {}",labelCategory.size() );
        return labelCategory;
    }
}
