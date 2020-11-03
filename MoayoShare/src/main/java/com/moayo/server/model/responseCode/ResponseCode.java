package com.moayo.server.model.responseCode;

/**
 * 응답에서 응답코드가 필요한 요청을 위한 응답코드.
 *
 * @author gilwoongkang
 */
public enum ResponseCode {
    /**
     * 성공은 0을, 실패는 1을 가진다. (추후 더 추가할 예정)
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
