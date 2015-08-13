package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/9.
 */
public class WindForce {

    private Integer pkId;
    private Integer forceNo;
    private String nameZh;
    private String nameEn;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getForceNo() {
        return forceNo;
    }

    public void setForceNo(Integer forceNo) {
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
