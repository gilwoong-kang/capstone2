package com.moayo.server.service.concrete;

import com.moayo.server.dao.Post;
import com.moayo.server.model.CategoryPostModel;
import com.moayo.server.model.PostModel;
import com.moayo.server.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

    @Override
    public int insertPost(PostModel[] postModels, CategoryPostModel[] categoryPostModels){
        try{
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
            logger.debug("post insert success : {}",count);
            return count;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
}
