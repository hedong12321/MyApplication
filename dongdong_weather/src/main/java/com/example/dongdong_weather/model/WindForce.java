package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/9.
 */
public class WindForce {

    private int pkId;
    private int forceNo;
    private String nameZh;
    private String nameEn;

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public int getForceNo() {
        return forceNo;
    }

    public void setForceNo(int forceNo) {
        this.forceNo = forceNo;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
