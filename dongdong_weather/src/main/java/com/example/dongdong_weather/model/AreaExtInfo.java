package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/11.
 */
public class AreaExtInfo {

    private int pkId;
    private int areaId;
    private int cityOrder;
    private String cityAreaCode;
    private String postcode;
    private float latitude;
    private float longitude;
    private float altitude;
    private String radarNo;
    private String timeZone;

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getCityOrder() {
        return cityOrder;
    }

    public void setCityOrder(int cityOrder) {
        this.cityOrder = cityOrder;
    }

    public String getCityAreaCode() {
        return cityAreaCode;
    }

    public void setCityAreaCode(String cityAreaCode) {
        this.cityAreaCode = cityAreaCode;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
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
