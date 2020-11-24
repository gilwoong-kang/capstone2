package com.moayo.server.model.responseCode;

public enum ResponseCode {
    /**
     * 성공은 0을, 실패는 1을 가진다.
     */
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
