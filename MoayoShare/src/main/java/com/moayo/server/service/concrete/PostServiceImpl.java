package com.moayo.server.service.concrete;

import com.moayo.server.dao.Post;
import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.model.PostModel;
import com.moayo.server.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceImpl implements PostService {
    @Autowired
    Post post;

    private Logger logger;

    public PostServiceImpl() {
        this.logger = LogManager.getLogger();
    }

    @Override
    public PostModel[] getPost(int dogamId, CategoryPostModel[] categoryPostModels) {
        try{
            PostModel[] postModels = new PostModel[categoryPostModels.length];
            for(int i = 0;i<postModels.length;i++){
                postModels[i] = post.getPost(categoryPostModels[i].getCo_postId());
            }
            logger.debug("{} post read. : {}",dogamId,postModels.length);
            return postModels;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
}
