package com.moayo.server.service.concrete;

import com.moayo.server.dao.CategoryPost;
import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.service.CategoryPostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryPostServiceImpl implements CategoryPostService {
    @Autowired
    CategoryPost categoryPost;

    private Logger logger;

    public CategoryPostServiceImpl(){this.logger = LogManager.getLogger();
    }
    @Override
    public CategoryPostModel[] getCategoryPost(int dogamId) {
        try{
            CategoryPostModel[] categoryPostModels = categoryPost.getByDogamId(dogamId);
            logger.debug("{} category - post read. : {}",dogamId,categoryPostModels.length);
            return categoryPostModels;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
}
