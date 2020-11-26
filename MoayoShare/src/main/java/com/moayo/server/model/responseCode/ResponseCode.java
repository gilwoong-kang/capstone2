package com.moayo.server.model.responseCode;

public enum ResponseCode {

    SUCCESS(0,"Success."),
    NOTEXIST_ID(901,"This id is Not Exist."),
    DATABASE_ERROR(902,"A database error has occured."),
    SEARCH_ERROR(903,"Search result wasn't found.");

    private int code;
    private String description;

    ResponseCode(int code , String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
