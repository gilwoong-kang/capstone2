//package com.moayo.server.service.concrete;
//
//
//import com.moayo.server.dao.*;
//import com.moayo.server.model.*;
//import com.moayo.server.service.XMLParsingService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class XMLParsingServiceImpl implements XMLParsingService {
//    @Autowired
//    DogamListDao dogamListDao;
//    @Autowired
//    CategoryDao categoryDao;
//    @Autowired
//    HashtagDao hashtagDao;
//    @Autowired
//    CategoryHashDao categoryHashDao;
//    @Autowired
//    PostDao postDao;
//    @Autowired
//    CategoryPostDao categoryPostDao;
//
//    private Logger logger;
//
//    public XMLParsingServiceImpl() {
//        logger = LoggerFactory.getLogger(XMLParsingServiceImpl.class);
//    }
//
//    private DogamListModel dogamInsert(Document document){
//        DogamListModel dogamListModel = new DogamListModel(
//                document.getElementsByTagName("title").item(0).getTextContent(),
//                document.getElementsByTagName("description").item(0).getTextContent(),
//                Integer.valueOf(document.getElementsByTagName("status").item(0).getTextContent()),
//                document.getElementsByTagName("password").item(0).getTextContent(),document.getElementsByTagName("writer").item(0).getTextContent());
//
//        dogamListDao.insertDogam(dogamListModel);
//        logger.info(dogamListModel.toString());
//        return dogamListModel;
//    }
//
//    private Map<Integer,CategoryModel> categoryInsert(Document document,DogamListModel dogamListModel){
//        Map<Integer, CategoryModel> categoryMap = new HashMap<Integer,CategoryModel>();
//
//        NodeList categories = document.getElementsByTagName("category");
//        for(int i = 0;i<categories.getLength();i++){
//            Element category = (Element) categories.item(i);
//            int parentId = Integer.valueOf(category.getElementsByTagName("categoryParentId").item(0).getTextContent());
//            int id = Integer.valueOf(category.getAttribute("id"));
//            CategoryModel model = null;
//            if(id == parentId){
//                model = new CategoryModel(
//                        dogamListModel.getCo_dogamId(),
//                        category.getElementsByTagName("categoryName").item(0).getTextContent(),
//                        Integer.valueOf(category.getElementsByTagName("categoryLevel").item(0).getTextContent()),
//                        0
//                );
//                categoryDao.foreignKeyOFF();
//                categoryDao.rootInsert(model);
//                categoryDao.foreignKeyON();
//                model.setCo_parentCategoryId(model.getCo_categoryId());
//                categoryDao.updateCategory(model);
//                categoryMap.put(id,model);
//            }else {
//                model = new CategoryModel(
//                        dogamListModel.getCo_dogamId(),
//                        category.getElementsByTagName("categoryName").item(0).getTextContent(),
//                        Integer.valueOf(category.getElementsByTagName("categoryLevel").item(0).getTextContent()),
//                        categoryMap.get(parentId).getCo_categoryId()
//                );
//                categoryDao.insertCategory(model);
//                categoryMap.put(id,model);
//            }
//            String[] hashtags = hashtagInsert(category,model);
//            StringBuilder tag = new StringBuilder();
//            for(String hashtag : hashtags){ tag.append("#").append(hashtag);}
//            logger.info(model.getCo_name() + " : "+ tag.toString());
//        }
//        return categoryMap;
//    }
//
//    private String[] hashtagInsert(Element category,CategoryModel model){
//        String[] hashtags = category.getElementsByTagName("hashtag").item(0).getTextContent().split("#");
//        for(String hashtag : hashtags){
//            if(!hashtag.equals("")){
//                try{
//                    hashtagDao.insertHashtag(new HashtagModel(hashtag));
//                    categoryHashDao.insertCategoryHashtag(new CategoryHashModel(model.getCo_dogamId(),model.getCo_categoryId(),hashtag));
//                }catch (Exception e){
//                    logger.error(hashtag + " : is duplicate.");
//                    categoryHashDao.insertCategoryHashtag(new CategoryHashModel(model.getCo_dogamId(),model.getCo_categoryId(),hashtag));
//                }
//            }
//        }
//        return hashtags;
//    }
//    private void postInsert(Document document,Map<Integer,CategoryModel> categoryMap){
//        NodeList posts = document.getElementsByTagName("post");
//        for(int i = 0;i < posts.getLength();i++){
//            Element post = (Element)posts.item(i);
//            PostModel postModel = new PostModel(post.getElementsByTagName("postUrl").item(0).getTextContent(),
//                    post.getElementsByTagName("imageUrl").item(0).getTextContent(),
//                    post.getElementsByTagName("postHashtag").item(0).getTextContent());
//            postDao.insertPost(postModel);
//            CategoryModel parent = categoryMap.get(Integer.valueOf(post.getElementsByTagName("postParentId").item(0).getTextContent()));
//            categoryPostDao.insertCategoryPost(new CategoryPostModel(parent.getCo_dogamId(),parent.getCo_categoryId(),postModel.getCo_postId()));
//        }
//    }
//    public DogamListModel insertData(Document document){
//        // dogam insert
//        DogamListModel dogamListModel = dogamInsert(document);
//        // categoryInsert
//        Map<Integer,CategoryModel> categoryMap = categoryInsert(document,dogamListModel);
//        // post Insert
//        postInsert(document,categoryMap);
//        return dogamListModel;
//    }
//}
