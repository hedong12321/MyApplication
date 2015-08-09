package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/9.
 */
public class WindDirection {

    private int pkId;
    private int directionNo;
    private String nameZh;
    private String nameEn;

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public int getDirectionNo() {
        return directionNo;
    }

    public void setDirectionNo(int directionNo) {
        this.directionNo = directionNo;
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
