package com.moayo.server.controller;

import com.moayo.server.model.*;
import com.moayo.server.model.responseCode.ResponseCode;
import com.moayo.server.service.DogamInfoService;
import com.moayo.server.service.DogamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.Exception.NoDogamIdException;
import util.JSONReturn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gilwoong kang
 * */
@RestController
public class MainController {

    @Autowired
    DogamInfoService dogamInfoService;
    @Autowired
    DogamService dogamService;

    private static Logger logger = null;

    public MainController(){
        System.setProperty("log4j.configurationFile","log4j2.xml");
        logger = LogManager.getLogger();
    }

    /**
     * 현재 데이터베이스에 존재하는 모든 도감에 관한 정보를 반환한다.
     * @return 도감 모델을 리스트에 담아 리턴한다. 도감 정보는 id/title/description/status/password/writer/like/date이다.
     * @see DogamInfoModel
     * description에 소개와 함께 이미지 URL이 함께 전달한다.
     * */
    @RequestMapping(value = "/getDogamList",method = RequestMethod.GET)
    public List<DogamInfoModel> getDogamList(){
        try{
            logger.debug("Request getDogamList");
            return dogamInfoService.getAllDogamInfo();
        }catch (NullPointerException e){
            logger.error("Service return is NULL.");
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * dogamId 값과 일치하는 데이터베이스 내부 dogam의 like 수치를 하나 올린다.
     * @param dogamId
     * @return 올바르게 처리 되었다면 0000을, 오류가 있다면 0001을 도감 id와 함께 리턴한다.
     * */
    @RequestMapping(value = "/dogamLike", method = RequestMethod.GET)
    public JSONReturn like(@RequestParam int dogamId){
        try{
            logger.info("DogamId : " + dogamId + " Like.");
            dogamInfoService.like(dogamId);
        }catch (NoDogamIdException e){
            logger.error("Dogam Id Error : " + dogamId);
            return new JSONReturn(ResponseCode.NOTEXIST_ID.getCode(),dogamId);
        }catch (MyBatisSystemException e){
            logger.error("Database System Error : {}",e.getMessage());
            return new JSONReturn(ResponseCode.DATABASE_ERROR.getCode(),dogamId);
        }
        return new JSONReturn(ResponseCode.SUCCESS.getCode(),dogamId);
    }

    /**
     * dogamId 값과 일치하는 데이터베이스 내부 dogam의 like 수치를 하나 내린다.
     * @param dogamId
     * @return 올바르게 처리 되었다면 0000을, 오류가 있다면 0001을 도감의 id와 함 리턴한다.
     * */
    @RequestMapping(value = "/dogamDislike", method = RequestMethod.GET)
    public JSONReturn disLike(@RequestParam int dogamId){
        try{
            logger.info("DogamId : " + dogamId + " DisLike.");
            dogamInfoService.disLike(dogamId);
        }catch (NoDogamIdException e){
            logger.error("Dogam Id Error : " + dogamId);
            return new JSONReturn(ResponseCode.NOTEXIST_ID.getCode(),dogamId);
        }catch (MyBatisSystemException e){
            logger.error("Database System Error : {}",e.getMessage());
            return new JSONReturn(ResponseCode.DATABASE_ERROR.getCode(),dogamId);
        }
        return new JSONReturn(ResponseCode.SUCCESS.getCode(),dogamId);
    }

    /**
     * dogamId를 입력받아 데이터베이스에 조회하여 dogam 정보를 반환한다.
     * @param dogamId 도감의 id를 입력받는다.
     * @return 찾은 도감을 반환한다. 도감 정보 : category/hashtag/post & 연결정보
     * */
    @RequestMapping(value = "/getDogam",method = RequestMethod.GET)
    public DogamModel getDogam(HttpServletRequest req,HttpServletResponse res,@RequestParam int dogamId){
        logger.info(req.getRequestedSessionId()+" : "+dogamId);
        DogamModel dogamModel = dogamService.getDogam(dogamId);
        if(dogamModel == null){
            logger.error("get Dogam Error : {}",dogamId);
            dogamModel = new DogamModel();
            dogamModel.setDogamInfoModel(new DogamInfoModel("Dogam ERROR."));
            return dogamModel;
        }
        logger.info("{}/{} : Dogam Out.",dogamModel.getDogamInfoModel().getDogamId(),dogamModel.getDogamInfoModel().getTitle());
        return dogamModel;
    }

    /**
     * 공유하고자 하는 도감의 데이터를 받아 데이터베이스에 입력한다.
     * @param dogamModel 도감 모델의 정보를 입력받는다.
     * @see DogamModel
     * @return 성공시 0000, 실패시 0001 을 도감 id와 함께 리턴한다.
     * */
    @RequestMapping(value = "/shareDogam",method = RequestMethod.POST)
    public JSONReturn shareDogam(@RequestBody DogamModel dogamModel){
        try{
            logger.info(dogamModel.toString());
            dogamService.insertDogam(dogamModel);
        }catch (MyBatisSystemException e){
            logger.error("Database System Error : {}",e.getMessage());
            return new JSONReturn(ResponseCode.DATABASE_ERROR.getCode(),dogamModel.getDogamInfoModel().getDogamId());
        }
        logger.info("{}/{} : Dogam share Success.",dogamModel.getDogamInfoModel().getDogamId(),dogamModel.getDogamInfoModel().getTitle());
        return new JSONReturn(ResponseCode.SUCCESS.getCode(),dogamModel.getDogamInfoModel().getDogamId());
    }

    /**
     * dogamId를 받아 해당 키값이 데이터베이스에 존재한다면 관련 데이터를 삭제한다.
     * @param dogamId 도감의 id를 입력받는다.
     * @return 성공시 0000,실패시 0001을 도감 id와 함께 리턴한다.
     * */
    @RequestMapping(value = "/deleteDogam",method = RequestMethod.GET)
    public JSONReturn deleteDogam(@RequestParam int dogamId){
        logger.info("Delete Dogam ID : " + dogamId);
        try{
            if(!dogamInfoService.isDogamExist(dogamId))
                return new JSONReturn(ResponseCode.NOTEXIST_ID.getCode(),dogamId);
            dogamInfoService.deleteDogamInfo(dogamId);
        }catch (MyBatisSystemException e){
            logger.error("Database System Error : {}",e.getMessage());
            return new JSONReturn(ResponseCode.DATABASE_ERROR.getCode(),dogamId);
        }
        return new JSONReturn(ResponseCode.SUCCESS.getCode(),dogamId);
    }

    /**
     * 작성자 정보를 입력받아 작성자가 작성한 도감들을 리턴한다.
     * @param writer 도감의 작성자를 입력받는다.
     * @return 만일 존재하지 않는다면 0001의 코드값을 list 첫번째 객체 타이틀에 담아 리턴시킨다.
     * */
    @RequestMapping(value = "/getDogamWriterName",method = RequestMethod.GET)
    public List<DogamInfoModel> getDogamByUserName(@RequestParam String writer){
        logger.info("Writer dogam searching : {}",writer);
        List<DogamInfoModel> dogamInfoModels =  dogamInfoService.getDogamByWriterName(writer);
        logger.debug("Writer's Dogam search success : {}", dogamInfoModels.size());
        if(dogamInfoModels.isEmpty() || dogamInfoModels == null){
            logger.warn("Writer is NOT EXIST : {}" , writer);
            List<DogamInfoModel> errorList = new ArrayList<DogamInfoModel>();
            errorList.add(new DogamInfoModel(ResponseCode.SEARCH_ERROR.toString()));
            return errorList;
        }
        return dogamInfoModels;
    }

    /**
     * 특정 키워드가 description에 포함된 도감을 찾아 리턴한다.
     * @param keyword 찾고자 하는 카워드를 입력받는다.
     * @return 만일 존재하지 않는다면 0001의 코드값을 list 첫번째 객체 타이틀에 담아 리턴시킨다.
     * */
    @RequestMapping(value = "/getDogamKeyword" , method = RequestMethod.GET)
    public List<DogamInfoModel> getDogamByKeyword(@RequestParam String keyword){
        logger.info("Keyword : " + keyword);
        List<DogamInfoModel> dogamInfoModels =  dogamInfoService.getDogamByKeyword(keyword);
        logger.debug("Keyword Search result : {}", dogamInfoModels.size());
        if(dogamInfoModels.isEmpty() || dogamInfoModels == null){
            logger.warn("Keyword is NOT Find : {}" ,keyword);
            List<DogamInfoModel> errorList = new ArrayList<DogamInfoModel>();
            errorList.add(new DogamInfoModel(ResponseCode.SEARCH_ERROR.toString()));
            return errorList;
        }
        return dogamInfoModels;
    }
}
