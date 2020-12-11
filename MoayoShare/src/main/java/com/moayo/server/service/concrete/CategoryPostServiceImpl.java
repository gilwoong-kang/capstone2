package com.moayo.server.service.concrete;

import com.moayo.server.dao.CategoryPost;
import com.moayo.server.model.CategoryModel;
import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.service.CategoryPostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    @Override
    public void labelingCategoryPost(CategoryPostModel[] categoryPostModels, int origin, CategoryModel categoryModel){
        for(CategoryPostModel categoryPostModel : categoryPostModels){
            if(categoryPostModel.getCo_categoryId() == origin){
                categoryPostModel.setCo_categoryId(categoryModel.getCo_categoryId());
                categoryPostModel.setCo_dogamId(categoryModel.getCo_dogamId());
                logger.trace("{} category - post data labeling {} ",categoryModel,categoryPostModel.getCo_postId());
            }
        }
    }

    @Override
    public int insertCategoryPost(CategoryPostModel[] categoryPostModels){
        try{
            if(categoryPostModels.length != 0) {
                long rows = categoryPost.insertAll(categoryPostModels);
                logger.debug("category post insert success : {}",rows);
                return (int)rows;
            }else{
                logger.debug("Post is Empty : {}",categoryPostModels.toString());
                return -1;
            }
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR. : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }

}
