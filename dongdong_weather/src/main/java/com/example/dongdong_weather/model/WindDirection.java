package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/9.
 */
public class WindDirection {

    private Integer pkId;
    private Integer directionNo;
    private String nameZh;
    private String nameEn;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getDirectionNo() {
        return directionNo;
    }

    public void setDirectionNo(Integer directionNo) {
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
