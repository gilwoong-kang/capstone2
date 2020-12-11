package com.moayo.server.model;

import java.sql.Timestamp;

public class DogamInfoModel {

    private int co_dogamId;
    private String co_title;
    private String co_description;
    private int co_status;
    private String co_password;
    private String co_writer;
    private int co_like;
    private Timestamp co_date;

    /**
     * Constructor. 모든 필드값에 대함.
     * */
    public DogamInfoModel(int co_dogamId, String co_title, String co_description, int co_status, String co_password, String co_writer, int co_like) {
        this.co_dogamId = co_dogamId;
        this.co_title = co_title;
        this.co_description = co_description;
        this.co_status = co_status;
        this.co_password = co_password;
        this.co_writer = co_writer;
        this.co_like = co_like;
    }

    /**
     * 도감 id를 제외한 Constructor. 도감 id는 디비에서 가져와서 이용하거나 로직에서 따로 이용한다.
     * */
    public DogamInfoModel(String co_title, String co_description, int co_status, String co_password, String co_writer, int co_like) {
        this.co_title = co_title;
        this.co_description = co_description;
        this.co_status = co_status;
        this.co_password = co_password;
        this.co_writer = co_writer;
        this.co_like = co_like;
    }

    public DogamInfoModel() { }

    /**
     * 도감의 제목만 부여된 객체 생성. 주로 오류값을 전달할 때 이용한다.
     * */
    public DogamInfoModel(String co_title) {
        this.co_title = co_title;
    }

    /**
     * 이후로는 각 필드에 대한 get/set 메소드.
     * */
    public int getCo_like() {
        return co_like;
    }

    public void setCo_like(int co_like) {
        this.co_like = co_like;
    }

    public Timestamp getCo_date() {
        return co_date;
    }

    public void setCo_date(Timestamp co_date) {
        this.co_date = co_date;
    }

    public void setCo_writer(String co_writer) {
        this.co_writer = co_writer;
    }

    public String getCo_writer() {
        return co_writer;
    }

    public void setCo_title(String co_title) {
        this.co_title = co_title;
    }

    public String getCo_title() {
        return co_title;
    }

    public void setCo_password(String co_password) {
        this.co_password = co_password;
    }

    public String getCo_password() {
        return co_password;
    }

    public void setCo_description(String co_description) {
        this.co_description = co_description;
    }

    public int getCo_dogamId() {
        return co_dogamId;
    }

    public void setCo_dogamId(int co_dogamId) {
        this.co_dogamId = co_dogamId;
    }

    public int getCo_status() {
        return co_status;
    }

    public void setCo_status(int co_status) {
        this.co_status = co_status;
    }

    public String getCo_description() {
        return co_description;
    }

    /**
     * model의 toString 리턴 형태는 JSON형태로 한다.
     * @return 도감의 정보를 리턴한다.
     * */
    @Override
    public String toString() {
        return "DogamInfoModel{" +
                "co_dogamId=" + co_dogamId +
                ", co_title='" + co_title + '\'' +
                ", co_description='" + co_description + '\'' +
                ", co_status=" + co_status +
                ", co_password='" + co_password + '\'' +
                ", co_writer='" + co_writer + '\'' +
                ", co_like=" + co_like +
                ", co_date=" + co_date +
                '}';
    }
}
