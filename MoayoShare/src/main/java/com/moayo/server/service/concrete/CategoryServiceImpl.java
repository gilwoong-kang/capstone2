package com.moayo.server.service.concrete;

import com.moayo.server.dao.Category;
import com.moayo.server.model.CategoryModel;
import com.moayo.server.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    Category categoryDao;

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
}
