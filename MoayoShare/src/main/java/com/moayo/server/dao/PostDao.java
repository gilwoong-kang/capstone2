package com.moayo.server.dao;

import com.moayo.server.model.PostModel;

import java.util.List;

public interface PostDao {
    long insertPost(PostModel postModel);
    long updatePost(PostModel postModel);
    long deletePost(PostModel postModel);
    long deletePostById(int postId);
    PostModel getPost(int postId);
    List<PostModel> getAllPost();

}
