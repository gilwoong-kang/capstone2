package com.moayo.server.service.concrete;

import com.moayo.server.dao.DogamList;
import com.moayo.server.model.DogamInfoModel;
import com.moayo.server.service.DogamInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DogamInfoServiceImpl implements DogamInfoService {
    @Autowired
    DogamList dogamListDao;

    private Logger logger;

    public DogamInfoServiceImpl(){this.logger = LogManager.getLogger();}

    @Override
    public boolean isDogamExist(int dogamId) {
        try {
            DogamInfoModel dogamInfoModel = dogamListDao.getDogamById(dogamId);
            if(dogamInfoModel == null){
                logger.warn("{} dogam is NOT EXIST.",dogamId);
                return false;
            }
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR");
            logger.fatal(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public DogamInfoModel getDogamInfoModel(int dogamId) {
        try{
            DogamInfoModel dogamInfoModel = dogamListDao.getDogamById(dogamId);
            logger.debug("{} dogam info read. : {}",dogamId,dogamInfoModel.getCo_title());
            return dogamInfoModel;
        }catch (MyBatisSystemException e){
            logger.fatal("DB ERROR : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DogamInfoModel> getAllDogamInfo() {
        try{
            List<DogamInfoModel> dogamInfoModels = dogamListDao.getAllDogam();
            logger.debug("All dogam Info read. : {}",dogamInfoModels.size());
            if(dogamInfoModels == null){ throw new NullPointerException();}
            return dogamInfoModels;
        }catch (MyBatisSystemException e){
                logger.fatal("Database ERROR.");
                logger.fatal(e.getMessage());
                return null;
        }
    }

    @Override
    public void deleteDogamInfo(int dogamId) {
        try{
            dogamListDao.deleteDogamById(dogamId);
            logger.debug("Deleting Dogam success.");
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DogamInfoModel> getDogamByWriterName(String writer) {
        try{
            List<DogamInfoModel> dogamInfoModels = dogamListDao.getDogamByWriterName(writer);
            logger.debug("{} writer's dogam read : {}",writer,dogamInfoModels.size());
            return dogamInfoModels;
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
    }

    @Override
    public List<DogamInfoModel> getDogamByKeyword(String keyword) {
        try{
            List<DogamInfoModel> dogamInfoModels= dogamListDao.getDogamByDescriptionSearch(keyword);
            logger.debug("{} keyword dogam info : {}",keyword,dogamInfoModels.size());
            return dogamInfoModels;
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR.");
            logger.fatal(e.getMessage());
            return null;
        }
    }
    @Override
    public int insertDogamInfo(DogamInfoModel dogamInfoModel){
        try{
            long result = dogamListDao.insertDogam(dogamInfoModel);
            if(result == 0) logger.error("{} : DogamList Insert Error");
            return (int)result;
        }catch (MyBatisSystemException e){
            logger.fatal("Database ERROR. : {}",this.getClass().getName());
            logger.fatal(e.getMessage());
            throw e;
        }
    }
}
