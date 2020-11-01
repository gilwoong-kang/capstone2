package com.moayo.server.model.responseCode;

public enum ResponseCode {

    SUCCESS("0000"),FAIL("0001");

    private String code;

    ResponseCode(String s) {
        this.code = s;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
