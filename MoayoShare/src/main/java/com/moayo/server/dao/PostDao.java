package com.moayo.server.dao;

import com.moayo.server.model.PostModel;

import java.util.List;

/**
 * MyBatis Mapper와 연결하는 interface.
 *
 * @author gilwoongkang
 */
public interface PostDao {
    /**
     * 특정 게시물 정보를 insert한다.
     * @param postModel insert 하고자 하는 게시물 정보의 model.
     * @see PostModel
     * @return insert row.
     */
    long insertPost(PostModel postModel);

    /**
     * 특정 게시물 정볼를 update한다.
     * @param postModel update하고자 하는 게시물 정보의 model.
     * @return update row.
     */
    long updatePost(PostModel postModel);

    /**
     * 특정 게시물 정보를 삭제한다.
     * @param postModel 삭제하고자 하는 게시물 정보의 model.
     * @return delete row.
     */
    long deletePost(PostModel postModel);

    /**
     * 특정 게시물 정보를 삭제한다.
     * @param postId 삭제하고자 하는 게시물 정보의 id값.
     * @return delete row.
     */
    long deletePostById(int postId);

    /**
     * 특정 게시물 정보를 가져온다.
     * @param postId 가져오고자 하는 게시물 정보의 id값.
     * @return 게시물 정보를 담고있는 model.
     */
    PostModel getPost(int postId);

    /**
     * 모든 게시물 정보를 가져온다.
     * @return 게시물 정보를 담는 model들의 list.
     */
    List<PostModel> getAllPost();

}
