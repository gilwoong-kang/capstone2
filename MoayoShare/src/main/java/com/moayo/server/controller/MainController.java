package com.moayo.server.controller;


import com.moayo.server.model.*;
import com.moayo.server.service.JSONParsingService;
import com.moayo.server.service.concrete.ShareService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JSONReturn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    ShareService service;   // get service
    @Autowired
    JSONParsingService jsonParsingService; // general service


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
            return service.getDogamList();
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
            jsonParsingService.like(dogamId);
            return new JSONReturn(0000,dogamId);
        }catch (Exception e){
            logger.error("Dogam Id Error : " + dogamId);
            return new JSONReturn(0001,dogamId);
        }
    }

    @RequestMapping(value = "/dogamDislike", method = RequestMethod.GET)
    public JSONReturn disLike(@RequestParam int dogamId){
        try{
            logger.info("DogamId : " + dogamId + " DisLike.");
            jsonParsingService.disLike(dogamId);
            return new JSONReturn(0000,dogamId);
        }catch (Exception e){
            logger.error("Dogam Id Error : " + dogamId);
            return new JSONReturn(0001,dogamId);
        }
    }

    @RequestMapping(value = "/getDogam",method = RequestMethod.GET)
    public DogamModel getDogam(HttpServletRequest req,HttpServletResponse res,@RequestParam int dogamId){

        logger.info(req.getRequestedSessionId()+" : "+dogamId);
        DogamModel dogamModel = service.getDogam(dogamId);
        logger.info(dogamModel.getDogamListModel().toString());
        return dogamModel;
    }

    @RequestMapping(value = "/shareDogam",method = RequestMethod.POST)
    public JSONReturn shareDogam(@RequestBody DogamModel dogamModel){
        logger.info(dogamModel.toString());
        try{
            jsonParsingService.insertData(dogamModel);
        }catch (Exception e){
            return new JSONReturn(0001,dogamModel.getDogamListModel().getCo_dogamId());
        }
        return new JSONReturn(0000,dogamModel.getDogamListModel().getCo_dogamId());
    }

    @RequestMapping(value = "/deleteDogam",method = RequestMethod.GET)
    public JSONReturn deleteDogam(@RequestParam int dogamId){
        logger.info("Delete Dogam ID : " + dogamId);
        if(!service.isDogam(dogamId))
            return new JSONReturn(0001,dogamId);
        service.deleteDogam(dogamId);
        return new JSONReturn(0000,dogamId);
    }

    @RequestMapping(value = "/getDogamWriterName",method = RequestMethod.GET)
    public List<DogamListModel> getDogamByUserName(@RequestParam String writer){
        logger.info(writer);
        List<DogamListModel> dogamListModels =  service.getDogamByWriterName(writer);
        if(dogamListModels.isEmpty() || dogamListModels == null){
            List<DogamListModel> errorList = new ArrayList<DogamListModel>();
            errorList.add(new DogamListModel("{0001,\"writer wrong, Not in DB\"}"));
            return errorList;
        }
        return dogamListModels;
    }
    @RequestMapping(value = "/getDogamKeyword" , method = RequestMethod.GET)
    public List<DogamListModel> getDogamByKeyword(@RequestParam String keyword){
        logger.info("Keyword : " + keyword);
        List<DogamListModel> dogamListModels =  service.getDogamByKeyword(keyword);
        if(dogamListModels.isEmpty() || dogamListModels == null){
            List<DogamListModel> errorList = new ArrayList<DogamListModel>();
            errorList.add(new DogamListModel("{0001,\"search result is nothing.\"}"));
            return errorList;
        }
        return dogamListModels;
    }
}
