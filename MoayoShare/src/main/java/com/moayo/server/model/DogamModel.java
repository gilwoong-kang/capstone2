package com.moayo.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Exception.getDogamDBException;

import java.util.Arrays;

public class DogamModel {
    private DogamListModel dogamListModel;
    private CategoryModel[] categoryModels;
    private PostModel[] postModels;
    private HashtagModel[] hashtagModels;

    private CategoryPostModel[] categoryPostModels;
    private CategoryHashModel[] categoryHashModels;

    private Logger logger = LogManager.getLogger();

    public DogamModel(DogamListModel dogamListModel, CategoryModel[] categoryModels, PostModel[] postModels, HashtagModel[] hashtagModels, CategoryPostModel[] categoryPostModels, CategoryHashModel[] categoryHashModels) {
        this.dogamListModel = dogamListModel;
        this.categoryModels = categoryModels;
        this.postModels = postModels;
        this.hashtagModels = hashtagModels;
        this.categoryPostModels = categoryPostModels;
        this.categoryHashModels = categoryHashModels;
    }

    public DogamModel() {
    }

    @Override
    public String toString() {
        return "DogamModel{" +
                "dogamListModel=" + dogamListModel +
                ", categoryModels=" + Arrays.toString(categoryModels) +
                ", postModels=" + Arrays.toString(postModels) +
                ", hashtagModels=" + Arrays.toString(hashtagModels) +
                ", categoryPostModels=" + Arrays.toString(categoryPostModels) +
                ", categoryHashModels=" + Arrays.toString(categoryHashModels) +
                '}';
    }

    public void setDogamListModel(DogamListModel dogamListModel){
        if(dogamListModel == null){
            logger.warn("{} dogamListModel is NULL. 도감 아이디 값이 잘못되었습니다.",this.getClass().getName());
        }
        this.dogamListModel = dogamListModel;
    }

    public void setCategoryModels(CategoryModel[] categoryModels) {
        if(categoryModels.length == 0){
            logger.warn("{} categoryModels is NULL",this.getClass().getName());
        }
        this.categoryModels = categoryModels;
    }

    public void setPostModels(PostModel[] postModels) {
        this.postModels = postModels;
    }

    public void setHashtagModels(HashtagModel[] hashtagModels) {
        if(hashtagModels.length == 0){
            logger.warn("{} hashtagModels is NULL",this.getClass().getName());
        }
        this.hashtagModels = hashtagModels;
    }

    public void setCategoryPostModels(CategoryPostModel[] categoryPostModels) {
        if(categoryPostModels.length == 0){
            logger.warn("{} categoryPostModels is NULL");
        }
        this.categoryPostModels = categoryPostModels;
    }

    public void setCategoryHashModels(CategoryHashModel[] categoryHashModels) {
        if(categoryHashModels.length == 0){
            logger.warn("{} categoryHashModels is NULL",this.getClass().getName());
        }
        this.categoryHashModels = categoryHashModels;
    }

    public DogamListModel getDogamListModel() {
        return dogamListModel;
    }

    public CategoryModel[] getCategoryModels() {
        return categoryModels;
    }

    public PostModel[] getPostModels() {
        return postModels;
    }

    public HashtagModel[] getHashtagModels() {
        return hashtagModels;
    }

    public CategoryPostModel[] getCategoryPostModels() {
        return categoryPostModels;
    }

    public CategoryHashModel[] getCategoryHashModels() {
        return categoryHashModels;
    }
}
