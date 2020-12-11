package com.moayo.server.service.concrete;

import com.moayo.server.dao.CategoryHash;
import com.moayo.server.model.CategoryHashModel;
import com.moayo.server.model.CategoryModel;
import com.moayo.server.service.CategoryHashService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;


public class CategoryHashServiceImpl implements CategoryHashService {
    @Autowired
    CategoryHash categoryHashDao;

    private Logger logger;

    public CategoryHashServiceImpl() {
        this.logger = LogManager.getLogger();
    }

    @Override
    public CategoryHashModel[] getCategoryHash(int dogamId) {
        try {
            CategoryHashModel[] categoryHashModels = categoryHashDao.getByDogamId(dogamId);
            logger.debug("{} category - hash read. : {}",dogamId,categoryHashModels.length);
            return categoryHashModels;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    @Override
    public void labelingCategoryHash(CategoryHashModel[] categoryHashModels, int origin, CategoryModel categoryModel){
        for(CategoryHashModel categoryHashModel : categoryHashModels){
            if(categoryHashModel.getco_categoryId() == origin){
                categoryHashModel.setco_dogamId(categoryModel.getCo_dogamId());
                categoryHashModel.setco_categoryId(categoryModel.getCo_categoryId());
                logger.trace("{} category - hash data labeling {} ",categoryModel,categoryHashModel.getco_hashtag());
            }
        }
    }

    @Override
    public int insertCategoryHash(CategoryHashModel[] categoryHashModels){
        try{
            if(categoryHashModels.length != 0){
                long rows = categoryHashDao.insertAll(categoryHashModels);
                logger.debug("category hash insert success : {}",rows);
                return (int)rows;
            }else{
                logger.debug("CategoryHash is Empty : {}",categoryHashModels.toString());
                return -1;
            }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR. : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
}
