package com.moayo.server.service.concrete;

import com.moayo.server.dao.CategoryHash;
import com.moayo.server.model.CategoryHashModel;
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
}
