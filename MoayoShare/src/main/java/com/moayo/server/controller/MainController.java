package com.moayo.server.controller;


import com.moayo.server.model.*;
import com.moayo.server.service.JSONParsingService;
import com.moayo.server.service.concrete.ShareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    ShareService service;
    @Autowired
    JSONParsingService jsonParsingService;

    static int count = 0;
    static long avg = 0;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/analysis",method=RequestMethod.GET)
    public String an(){
        int c = count;
        long a = avg;
        count = 0;
        avg = 0;
        return "Count : " + c + ", avg : " + a/c;
    }

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

    @RequestMapping(value = "/getDogamList",method = RequestMethod.GET)
    public List<DogamListModel> getDogamList(){
//        logger.info("getAllDogam");
        long startTime = System.currentTimeMillis();
        List<DogamListModel> dogamListModels = service.getDogamList();
        long endTime = System.currentTimeMillis();
        logger.info("get Dogam List/Time : " + (endTime - startTime));
        avg += endTime - startTime;
        count++;
        return dogamListModels;
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
