package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/11.
 */
public class AreaExtInfo {

    private Integer pkId;
    private String areaId;
    private Integer cityOrder;
    private String cityAreaCode;
    private String postcode;
    private String latitude;
    private String longitude;
    private String altitude;
    private String radarNo;
    private String timeZone;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
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
