package com.moayo.server.service.concrete;

import com.moayo.server.dao.DogamList;
import com.moayo.server.model.DogamInfoModel;
import com.moayo.server.service.DogamInfoService;
import com.mysql.cj.jdbc.JdbcConnection;
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
        return null;
    }

    @Override
    public int deleteDogamInfo(int dogamId) {
        return 0;
    }

    @Override
    public List<DogamInfoModel> getDogamByWriterName(String writer) {
        return null;
    }

    @Override
    public List<DogamInfoModel> getDogamByKeyword(String keyword) {
        return null;
    }
}
