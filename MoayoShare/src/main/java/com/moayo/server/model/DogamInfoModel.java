package com.moayo.server.model;

import java.sql.Timestamp;

public class DogamInfoModel {

    private int dogamId;
    private String title;
    private String description;
    private int status;
    private String password;
    private String writer;
    private int like;
    private Timestamp date;

    /**
     * Constructor. 모든 필드값에 대함.
     * */
    public DogamInfoModel(int dogamId, String title, String description, int status, String password, String writer, int like) {
        this.dogamId = dogamId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.password = password;
        this.writer = writer;
        this.like = like;
    }

    /**
     * 도감 id를 제외한 Constructor. 도감 id는 디비에서 가져와서 이용하거나 로직에서 따로 이용한다.
     * */
    public DogamInfoModel(String title, String description, int status, String password, String writer, int like) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.password = password;
        this.writer = writer;
        this.like = like;
    }

    public DogamInfoModel() { }

    /**
     * 도감의 제목만 부여된 객체 생성. 주로 오류값을 전달할 때 이용한다.
     * */
    public DogamInfoModel(String title) {
        this.title = title;
    }

    /**
     * 이후로는 각 필드에 대한 get/set 메소드.
     * */
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDogamId() {
        return dogamId;
    }

    public void setDogamId(int dogamId) {
        this.dogamId = dogamId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return 도감의 정보를 리턴한다.
     * */
    @Override
    public String toString() {
        return "DogamInfoModel{" +
                "dogamId=" + dogamId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", writer='" + writer + '\'' +
                ", like=" + like +
                ", date=" + date +
                '}';
    }
}
