package com.moayo.server.model;

import java.util.Arrays;

public class DogamModel {
    private DogamListModel dogamListModel;
    private CategoryModel[] categoryModels;
    private PostModel[] postModels;
    private HashtagModel[] hashtagModels;

    private CategoryPostModel[] categoryPostModels;
    private CategoryHashModel[] categoryHashModels;

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

    public void setDogamListModel(DogamListModel dogamListModel) {
        this.dogamListModel = dogamListModel;
    }

    public void setCategoryModels(CategoryModel[] categoryModels) {
        this.categoryModels = categoryModels;
    }

    public void setPostModels(PostModel[] postModels) {
        this.postModels = postModels;
    }

    public void setHashtagModels(HashtagModel[] hashtagModels) {
        this.hashtagModels = hashtagModels;
    }

    public void setCategoryPostModels(CategoryPostModel[] categoryPostModels) {
        this.categoryPostModels = categoryPostModels;
    }

    public void setCategoryHashModels(CategoryHashModel[] categoryHashModels) {
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
