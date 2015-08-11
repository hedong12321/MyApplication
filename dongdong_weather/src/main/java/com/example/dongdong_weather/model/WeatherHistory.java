package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/11.
 */
public class WeatherHistory {

    private int pkId;
    private int areaId;
    private String forecastDate;
    private int dayWeatherNo;
    private int nightWeatherNo;
    private int dayTemperature;
    private int nightTemperature;
    private int dayWindDirNo;
    private int nightWindDirNo;
    private int dayWindForceNo;
    private int nightWindForceNo;
    private String sunriseSunset;
    private String publishTime;

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

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public int getDayWeatherNo() {
        return dayWeatherNo;
    }

    public void setDayWeatherNo(int dayWeatherNo) {
        this.dayWeatherNo = dayWeatherNo;
    }

    public int getNightWeatherNo() {
        return nightWeatherNo;
    }

    public void setNightWeatherNo(int nightWeatherNo) {
        this.nightWeatherNo = nightWeatherNo;
    }

    public int getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(int dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public int getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(int nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public int getDayWindDirNo() {
        return dayWindDirNo;
    }

    public void setDayWindDirNo(int dayWindDirNo) {
        this.dayWindDirNo = dayWindDirNo;
    }

    public int getNightWindDirNo() {
        return nightWindDirNo;
    }

    public void setNightWindDirNo(int nightWindDirNo) {
        this.nightWindDirNo = nightWindDirNo;
    }

    public int getDayWindForceNo() {
        return dayWindForceNo;
    }

    public void setDayWindForceNo(int dayWindForceNo) {
        this.dayWindForceNo = dayWindForceNo;
    }

    public int getNightWindForceNo() {
        return nightWindForceNo;
    }

    public void setNightWindForceNo(int nightWindForceNo) {
        this.nightWindForceNo = nightWindForceNo;
    }

    public String getSunriseSunset() {
        return sunriseSunset;
    }

    public void setSunriseSunset(String sunriseSunset) {
        this.sunriseSunset = sunriseSunset;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
