package com.moayo.server.service.concrete;

import com.moayo.server.dao.*;
import com.moayo.server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareService {
    @Autowired
    DogamListDao dogamListDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryHashDao categoryHashDao;
    @Autowired
    CategoryPostDao categoryPostDao;
    @Autowired
    PostDao postDao;

    private Logger logger;

    public ShareService() {
        this.logger = LoggerFactory.getLogger(ShareService.class);
    }

    public boolean isDogam(int dogamId){
        DogamListModel dogamListModel = dogamListDao.getDogamById(dogamId);
        if(dogamListModel == null)
            return false;
        return true;
    }

    public DogamModel getDogam(int dogamId){
        DogamModel dogamModel = new DogamModel();
        dogamModel.setDogamListModel(dogamListDao.getDogamById(dogamId));
        dogamModel.setCategoryModels(categoryDao.getCategoryByDogamId(dogamId));

        CategoryHashModel[] categoryHashModels = categoryHashDao.getByDogamId(dogamId);
        dogamModel.setCategoryHashModels(categoryHashModels);

        CategoryPostModel[] categoryPostModels = categoryPostDao.getByDogamId(dogamId);
        dogamModel.setCategoryPostModels(categoryPostDao.getByDogamId(dogamId));

        PostModel[] postModels = new PostModel[categoryPostModels.length];
        for(int i = 0;i<postModels.length;i++){
            postModels[i] = postDao.getPost(categoryPostModels[i].getCo_postId());
        }
        dogamModel.setPostModels(postModels);

        HashtagModel[] hashtagModels = new HashtagModel[categoryHashModels.length];
        for(int i =0 ;i<hashtagModels.length;i++){
            hashtagModels[i] = new HashtagModel(categoryHashModels[i].getco_hashtag());
        }
        dogamModel.setHashtagModels(hashtagModels);
        logger.info(dogamModel.getDogamListModel().toString());
        return dogamModel;
    }
    public List<DogamListModel> getDogamList(){
        return dogamListDao.getAllDogam();
    }

    public void deleteDogam(int dogamId){
        dogamListDao.deleteDogamById(dogamId);
    }

    public List<DogamListModel> getDogamByWriterName(String writer){
        return dogamListDao.getDogamByWriterName(writer);
    }
    public List<DogamListModel> getDogamByKeyword(String keyword){
        return dogamListDao.getDogamByDescriptionSearch(keyword);
    }
}
