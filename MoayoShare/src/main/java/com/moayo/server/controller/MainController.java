package com.moayo.server.controller;


import com.moayo.server.model.*;
import com.moayo.server.model.responseCode.ResponseCode;
import com.moayo.server.service.CUDService;
import com.moayo.server.service.concrete.ReadService;
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

@RestController
public class MainController {

    @Autowired
    ReadService readService;   // get service
    @Autowired
    CUDService cudService; // general service


    private static Logger logger = null;

    /**
     * DI시 logger 객체 함께 주입
     * */
    public MainController(){
        // set Logger
        System.setProperty("log4j.configurationFile","log4j2.xml");
        logger = LogManager.getLogger();
    }

    /**
     * 현재 데이터베이스에 존재하는 모든 도감에 관한 정보를 반환한다.
     * id/title/description/status/password/writer/like/date
     * description에 소개와 함께 이미지 URL이 함께 전달한다.
     * */
    @RequestMapping(value = "/getDogamList",method = RequestMethod.GET)
    public List<DogamListModel> getDogamList(){
        logger.info("Request getDogamList");
        try{
            return readService.getDogamList();
        }catch (NullPointerException e){
            logger.error("Service return is NULL.");
            return null;
        }
    }

    /**
     * dogamId 값과 일치하는 데이터베이스 내부 dogam의 like 수치를 하나 올린다.
     * 올바르게 처리 되었다면 0000을, 오류가 있다면 0001을 리턴한다.
     * */
    @RequestMapping(value = "/dogamLike", method = RequestMethod.GET)
    public JSONReturn like(@RequestParam int dogamId){
        try{
            logger.info("DogamId : " + dogamId + " Like.");
            cudService.like(dogamId);
            return new JSONReturn(Integer.valueOf(ResponseCode.valueOf("SUCCESS").getCode()),dogamId);
        }catch (NoDogamIdException e){
            logger.error("Dogam Id Error : " + dogamId);
            return new JSONReturn(Integer.valueOf(ResponseCode.valueOf("FAIL").getCode()),dogamId);
        }catch (MyBatisSystemException e){
            return new JSONReturn(Integer.valueOf(ResponseCode.valueOf("FAIL").getCode()),dogamId);
        }
    }
    /**
     * dogamId 값과 일치하는 데이터베이스 내부 dogam의 like 수치를 하나 내린다.
     * 올바르게 처리 되었다면 0000을, 오류가 있다면 0001을 리턴한다.
     * */
    @RequestMapping(value = "/dogamDislike", method = RequestMethod.GET)
    public JSONReturn disLike(@RequestParam int dogamId){
        try{
            logger.info("DogamId : " + dogamId + " DisLike.");
            cudService.disLike(dogamId);
            return new JSONReturn(0000,dogamId);
        }catch (NoDogamIdException e){
            logger.error("Dogam Id Error : " + dogamId);
            return new JSONReturn(0001,dogamId);
        }catch (MyBatisSystemException e){
            return new JSONReturn(0001,dogamId);
        }
    }
    /**
     * dogamId를 입력받아 데이터베이스에 조회하여 dogam 정보를 반환한다.
     * category/hashtag/post & 연결정보
     * */
    @RequestMapping(value = "/getDogam",method = RequestMethod.GET)
    public DogamModel getDogam(HttpServletRequest req,HttpServletResponse res,@RequestParam int dogamId){
        logger.info(req.getRequestedSessionId()+" : "+dogamId);
        DogamModel dogamModel = readService.getDogam(dogamId);
        logger.info("{}/{} : Dogam Out.",dogamModel.getDogamListModel().getCo_dogamId(),dogamModel.getDogamListModel().getCo_title());
        return dogamModel;
    }
    /**
     * 공유하고자 하는 도감의 데이터를 받아 데이터베이스에 입력한다.
     * 성공시 0000, 실패시 0001 리턴.
     * */
    @RequestMapping(value = "/shareDogam",method = RequestMethod.POST)
    public JSONReturn shareDogam(@RequestBody DogamModel dogamModel){
        logger.info(dogamModel.toString());
        try{
            cudService.insertData(dogamModel);
        }catch (Exception e){
            return new JSONReturn(0001,dogamModel.getDogamListModel().getCo_dogamId());
        }
        return new JSONReturn(0000,dogamModel.getDogamListModel().getCo_dogamId());
    }
    /**
     * dogamId를 받아 해당 키값이 데이터베이스에 존재한다면 관련 데이터를 삭제한다.
     * 성공시 0000,실패시 0001리턴
     * */
    @RequestMapping(value = "/deleteDogam",method = RequestMethod.GET)
    public JSONReturn deleteDogam(@RequestParam int dogamId){
        logger.info("Delete Dogam ID : " + dogamId);
        if(!readService.isDogam(dogamId))
            return new JSONReturn(0001,dogamId);
        readService.deleteDogam(dogamId);
        return new JSONReturn(0000,dogamId);
    }
    /**
     * 작성자 정보를 입력받아 작성자가 작성한 도감들을 리턴한다.
     * 만일 존재하지 않는다면 타이틀에 0001의 코드값을 실어 리턴시킨다.
     * */
    @RequestMapping(value = "/getDogamWriterName",method = RequestMethod.GET)
    public List<DogamListModel> getDogamByUserName(@RequestParam String writer){
        logger.info(writer);
        List<DogamListModel> dogamListModels =  readService.getDogamByWriterName(writer);
        if(dogamListModels.isEmpty() || dogamListModels == null){
            List<DogamListModel> errorList = new ArrayList<DogamListModel>();
            errorList.add(new DogamListModel("{0001,\"writer wrong, Not in DB\"}"));
            return errorList;
        }
        return dogamListModels;
    }
    /**
     * 특정 키워드가 description에 포함된 도감을 찾아 리턴한다.
     * 만일 존재하지 않는다면 타이틀에 0001의 코드값을 실어 리턴시킨다.
     * */
    @RequestMapping(value = "/getDogamKeyword" , method = RequestMethod.GET)
    public List<DogamListModel> getDogamByKeyword(@RequestParam String keyword){
        logger.info("Keyword : " + keyword);
        List<DogamListModel> dogamListModels =  readService.getDogamByKeyword(keyword);
        if(dogamListModels.isEmpty() || dogamListModels == null){
            List<DogamListModel> errorList = new ArrayList<DogamListModel>();
            errorList.add(new DogamListModel("{0001,\"search result is nothing.\"}"));
            return errorList;
        }
        return dogamListModels;
    }
}
