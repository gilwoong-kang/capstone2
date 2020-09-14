package util;

public class JSONReturn {
    private int code;
    private int dogamId;

    public JSONReturn(int code,int dogamId) {
        this.code = code;
        this.dogamId = dogamId;
    }

    public int getDogamId() {
        return dogamId;
    }

    public void setDogamId(int dogamId) {
        this.dogamId = dogamId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "JSONReturn{" +
                "code=" + code +
                ", dogamId=" + dogamId +
                '}';
    }
}
