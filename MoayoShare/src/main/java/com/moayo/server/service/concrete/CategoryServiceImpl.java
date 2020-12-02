package com.moayo.server.service.concrete;

import com.moayo.server.dao.Category;
import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.CategoryModel;
import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.model.DogamInfoModel;
import com.moayo.server.service.CategoryHashService;
import com.moayo.server.service.CategoryPostService;
import com.moayo.server.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    Category categoryDao;
    @Autowired
    CategoryPostService categoryPostService;
    @Autowired
    CategoryHashService categoryHashService;

    private Logger logger;

    public CategoryServiceImpl(){ this.logger = LogManager.getLogger(); }

    @Override
    public CategoryModel[] getCategory(int dogamId) {
        try{
            CategoryModel[] categoryModels = categoryDao.getCategoryByDogamId(dogamId);
            logger.debug("{} category read. : {}",dogamId,categoryModels.length);
            return categoryModels;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
    @Override
    public Map<Integer,CategoryModel> insertCategory(CategoryModel[] categoryModels, DogamInfoModel dogamInfoModel,
                                                     CategoryPostModel[] categoryPostModels, CategoryHashModel[] categoryHashModels){
        Map<Integer,CategoryModel> categoryModelMap = new HashMap<Integer,CategoryModel>();
        logger.debug("{} category data relabeling",categoryModels.length);
        Map<Integer,List<CategoryModel>> levelLabelCategory = labelCategory(categoryModels); // 재 라벨링.
        logger.debug("{} Inserting category data",categoryModels.length);

        for(int i = 1; i<levelLabelCategory.keySet().size()+1;i++){
            for(CategoryModel categoryModel : levelLabelCategory.get(i)){
                int origin = 0;
                categoryModel.setCo_dogamId(dogamInfoModel.getCo_dogamId());
                origin = categoryModel.getCo_categoryId();
                if(categoryModel.getCo_categoryId() == categoryModel.getCo_parentCategoryId()) insertRootCategory(categoryModel);
                else insertGeneralCategory(categoryModel,categoryModelMap);
                categoryModelMap.put(origin,categoryModel);
                categoryPostService.insertCategoryPost(categoryPostModels,origin,categoryModel);
                categoryHashService.insertCategoryHash(categoryHashModels,origin,categoryModel);
            }
        }
        logger.info("{} dogam insert {} cateogries.",dogamInfoModel,categoryModelMap.size());
        return categoryModelMap;
    }

    private void insertRootCategory(CategoryModel categoryModel){
        try{
            categoryDao.foreignKeyOFF();
            logger.trace("foreignKey Off");
            long rows = categoryDao.insertCategory(categoryModel);
            categoryDao.foreignKeyON();
            logger.trace("foreignKey On");
            categoryModel.setCo_parentCategoryId(categoryModel.getCo_categoryId());
            categoryDao.updateCategory(categoryModel);
            logger.debug("{} Dogam root insert rows : {} ",categoryModel,rows);
        }catch (MyBatisSystemException e){
            logger.error("Category insert SQL ERROR : " + this.getClass().getName());
            logger.error(e.getMessage());
        }
    }

    private void insertGeneralCategory(CategoryModel categoryModel,Map<Integer,CategoryModel> categoryModelMap){
        try{
            categoryModel.setCo_parentCategoryId(categoryModelMap.get(categoryModel.getCo_parentCategoryId()).getCo_categoryId());
            categoryDao.insertCategory(categoryModel);
            logger.debug("{} category Insert : {}",categoryModel,categoryModel.getCo_categoryId());
        }catch (MyBatisSystemException e){
            logger.error("Category insert SQL ERROR : " + this.getClass().getName());
            logger.error(e.getMessage());
        }
    }

    /**
     * 서버 DB값에 맞는 ID 재 라벨링.
     * @param categoryModels insert 하려는 category의 정보들
     * @return 재 라벨링된 카테고리 map.
     */
    private Map<Integer, List<CategoryModel>> labelCategory(CategoryModel[] categoryModels){
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
