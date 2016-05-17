package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/9.
 */
public class AreaCode {

    private Integer pkId;
    private String areaId;
    private String nameEn;
    private String nameZh;
    private String districtEn;
    private String districtZh;
    private String provEn;
    private String provZh;
    private String nationEn;
    private String nationZh;

    private String sortLetters;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
    }

    public String getDistrictZh() {
        return districtZh;
    }

    public void setDistrictZh(String districtZh) {
        this.districtZh = districtZh;
    }

    public String getProvEn() {
        return provEn;
    }

    public void setProvEn(String provEn) {
        this.provEn = provEn;
    }

    public String getProvZh() {
        return provZh;
    }

    public void setProvZh(String provZh) {
        this.provZh = provZh;
    }

    public String getNationEn() {
        return nationEn;
    }

    public void setNationEn(String nationEn) {
        this.nationEn = nationEn;
    }

    public String getNationZh() {
        return nationZh;
    }

    public void setNationZh(String nationZh) {
        this.nationZh = nationZh;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
