package com.moayo.server.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DogamListModel {
    private int co_dogamId;
    private String co_title;
    private String co_description;
    private int co_status;
    private String co_password;
    private String co_writer;
    private int co_like;
    private Timestamp co_date;

    public DogamListModel(int co_dogamId, String co_title, String co_description, int co_status, String co_password, String co_writer, int co_like) {
        this.co_dogamId = co_dogamId;
        this.co_title = co_title;
        this.co_description = co_description;
        this.co_status = co_status;
        this.co_password = co_password;
        this.co_writer = co_writer;
        this.co_like = co_like;
    }

    public DogamListModel(String co_title, String co_description, int co_status, String co_password, String co_writer, int co_like) {
        this.co_title = co_title;
        this.co_description = co_description;
        this.co_status = co_status;
        this.co_password = co_password;
        this.co_writer = co_writer;
        this.co_like = co_like;
    }

    public DogamListModel() {

    }

    public DogamListModel(String co_title) {
        this.co_title = co_title;
    }

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

    @Override
    public String toString() {
        return "DogamListModel{" +
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
