package com.moayo.server.service.concrete;

import com.moayo.server.dao.*;
import com.moayo.server.model.*;
import com.moayo.server.service.DataInsertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Exception.NoDogamIdException;

import java.util.*;

@Service
public class DataDataInsertServiceImpl implements DataInsertService {

    @Autowired
    DogamList dogamList;
    @Autowired
    Category category;
    @Autowired
    Post post;
    @Autowired
    Hashtag hashtag;
    @Autowired
    CategoryPost categoryPost;
    @Autowired
    CategoryHash categoryHash;

    private Logger logger;

    public DataDataInsertServiceImpl() {
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
        try{
            if(dogamList.insertDogam(dogamModel.getDogamInfoModel()) == 0) logger.error("{} : DogamList Insert Error"); // category 재 라벨링 수행 이후 insert
            categoryInsert(dogamModel.getCategoryModels(), dogamModel.getDogamInfoModel(),dogamModel.getCategoryPostModels(),dogamModel.getCategoryHashModels());
            int postrows = postInsert(dogamModel.getPostModels(),dogamModel.getCategoryPostModels());
            logger.debug("{} post insert success : {}",dogamModel.getDogamInfoModel(),postrows);
            if(dogamModel.getHashtagModels().length != 0){
                int hashtagRows = hashtagInsert(dogamModel.getHashtagModels());
                logger.debug("{} hashtag insert success : {}",dogamModel.getDogamInfoModel(),hashtagRows);
            }
            if(dogamModel.getCategoryPostModels().length != 0) {
                long rows = categoryPost.insertAll(dogamModel.getCategoryPostModels());
                logger.debug("{} category post insert success : {}",dogamModel.getDogamInfoModel(),rows);
            }
            if(dogamModel.getCategoryHashModels().length != 0){
                long rows = categoryHash.insertAll(dogamModel.getCategoryHashModels());
                logger.debug("{} category hash insert success : {}",dogamModel.getDogamInfoModel(),rows);
            }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
        logger.info("Insert Success {} : {}", dogamModel.getDogamInfoModel().getCo_dogamId(), dogamModel.getDogamInfoModel().getCo_title());
    }

    @Override
    public void like(int dogamId) throws NoDogamIdException{
        try{
            if(dogamList.like(dogamId) == 0){ throw new NoDogamIdException(dogamId + " is NOT EXIST."); }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR. {}",e.getMessage());
            throw e;
        }
        logger.debug("{} like success.",dogamId);
    }

    @Override
    public void disLike(int dogamId) throws NoDogamIdException{
        try{
            if(dogamList.disLike(dogamId) == 0){ throw new NoDogamIdException(dogamId + " is NOT EXIST."); }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
        logger.debug("{} dislike success.",dogamId);
    }

    private int hashtagInsert(HashtagModel[] hashtagModels){
        int count = 0;
        for(HashtagModel hashtagModel : hashtagModels){
            try{
                long row = hashtag.insertHashtag(hashtagModel);
                logger.trace("{} hashtag insert : {}",hashtagModel.getCo_hashtag(),row);
                count++;
            }catch (Exception e){
                logger.error(hashtagModel.getCo_hashtag() + " : duplicate.");
                continue;
            }
        }
        return count;
    }

    private int postInsert(PostModel[] postModels,CategoryPostModel[] categoryPostModels){
        int count = 0;
        for(PostModel postModel : postModels){
            int origin = postModel.getCo_postId();
            long rows = post.insertPost(postModel);
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
                        category.foreignKeyOFF();
                        logger.trace("foreignKey Off");
                        long rows = category.insertCategory(categoryModel);
                        category.foreignKeyON();
                        logger.trace("foreignKey On");
                        categoryModel.setCo_parentCategoryId(categoryModel.getCo_categoryId());
                        category.updateCategory(categoryModel);
                        logger.debug("{} Dogam root insert rows : {} ",dogamInfoModel,rows);
                    }
                    // 일반적 경우
                    else{
                        categoryModel.setCo_parentCategoryId(categoryModelMap.get(categoryModel.getCo_parentCategoryId()).getCo_categoryId());
                        category.insertCategory(categoryModel);
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
