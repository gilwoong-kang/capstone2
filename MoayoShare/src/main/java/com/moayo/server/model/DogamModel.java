package com.moayo.server.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;

/**
 * 도감의 모든 데이터를 가진다. (DogamInfoModel, category정보, post정보, hashtag정보)
 * 클라이언트에게 도감의 정보를 넘겨주게 될때 해당 model을 이용해서 전달한다.
 * 해당 포맷은 클라이언트도 동일하게 가진다.
 *
 * @author gilwoongkang
 */
public class DogamModel {
    /**
     * 도감의 기본 정보를 가지는 dogamInfoModel
     */
    private DogamInfoModel dogamInfoModel;

    /**
     * 도감에 만들어져 있는 카테고리 정보들이 array에 담긴다.
     */
    private CategoryModel[] categoryModels;
    /**
     * 도감에 저장된 게시물 정보들이 array에 담긴다.
     */
    private PostModel[] postModels;

    /**
     * 도감에서 이용된 적 있는 해시태그값이 array에 담긴다.
     */
    private HashtagModel[] hashtagModels;

    /**
     * 카테고리와 게시물의 관계 표현을 위한 model list.
     */
    private CategoryPostModel[] categoryPostModels;

    /**
     * 카테고리와 해시태그의 관계 표현을 위한 model list.
     */
    private CategoryHashModel[] categoryHashModels;

    /**
     * 일부 NULL 상황에 대한 log 표현을 위한 logger.
     * log4j2 기반의 logger.
     */
    private Logger logger = LogManager.getLogger();

    public DogamModel(DogamInfoModel dogamInfoModel, CategoryModel[] categoryModels, PostModel[] postModels, HashtagModel[] hashtagModels, CategoryPostModel[] categoryPostModels, CategoryHashModel[] categoryHashModels) {
        this.dogamInfoModel = dogamInfoModel;
        this.categoryModels = categoryModels;
        this.postModels = postModels;
        this.hashtagModels = hashtagModels;
        this.categoryPostModels = categoryPostModels;
        this.categoryHashModels = categoryHashModels;
    }

    public DogamModel() {
    }

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return 모든 도감의 정보를 각 필드에 담아 리턴한다.
     */
    @Override
    public String toString() {
        return "DogamModel{" +
                "dogamInfoModel=" + dogamInfoModel +
                ", categoryModels=" + Arrays.toString(categoryModels) +
                ", postModels=" + Arrays.toString(postModels) +
                ", hashtagModels=" + Arrays.toString(hashtagModels) +
                ", categoryPostModels=" + Arrays.toString(categoryPostModels) +
                ", categoryHashModels=" + Arrays.toString(categoryHashModels) +
                '}';
    }

    /**
     * get/set 메소
     */
    public void setDogamInfoModel(DogamInfoModel dogamInfoModel){
        if(dogamInfoModel == null){
            logger.warn("{} dogamInfoModel is NULL. 도감 아이디 값이 잘못되었습니다.",this.getClass().getName());
        }
        this.dogamInfoModel = dogamInfoModel;
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

    public DogamInfoModel getDogamInfoModel() {
        return dogamInfoModel;
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
