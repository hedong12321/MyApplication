package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/11.
 */
public class AreaExtInfo {

    private Integer pkId;
    private Integer areaId;
    private Integer cityOrder;
    private String cityAreaCode;
    private String postcode;
    private Float latitude;
    private Float longitude;
    private Float altitude;
    private String radarNo;
    private String timeZone;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCityOrder() {
        return cityOrder;
    }

    public void setCityOrder(Integer cityOrder) {
        this.cityOrder = cityOrder;
    }

    public String getCityAreaCode() {
        return cityAreaCode;
    }

    public void setCityAreaCode(String cityAreaCode) {
        this.cityAreaCode = cityAreaCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public String getRadarNo() {
        return radarNo;
    }

    public void setRadarNo(String radarNo) {
        this.radarNo = radarNo;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
