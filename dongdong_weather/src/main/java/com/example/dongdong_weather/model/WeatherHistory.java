package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/11.
 */
public class WeatherHistory {

    private Integer pkId;
    private Integer areaId;
    private String forecastDate;
    private Integer dayWeatherNo;
    private Integer nightWeatherNo;
    private Integer dayTemperature;
    private Integer nightTemperature;
    private Integer dayWindDirNo;
    private Integer nightWindDirNo;
    private Integer dayWindForceNo;
    private Integer nightWindForceNo;
    private String sunriseSunset;
    private String publishTime;

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

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public Integer getDayWeatherNo() {
        return dayWeatherNo;
    }

    public void setDayWeatherNo(Integer dayWeatherNo) {
        this.dayWeatherNo = dayWeatherNo;
    }

    public Integer getNightWeatherNo() {
        return nightWeatherNo;
    }

    public void setNightWeatherNo(Integer nightWeatherNo) {
        this.nightWeatherNo = nightWeatherNo;
    }

    public Integer getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(Integer dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public Integer getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(Integer nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public Integer getDayWindDirNo() {
        return dayWindDirNo;
    }

    public void setDayWindDirNo(Integer dayWindDirNo) {
        this.dayWindDirNo = dayWindDirNo;
    }

    public Integer getNightWindDirNo() {
        return nightWindDirNo;
    }

    public void setNightWindDirNo(Integer nightWindDirNo) {
        this.nightWindDirNo = nightWindDirNo;
    }

    public Integer getDayWindForceNo() {
        return dayWindForceNo;
    }

    public void setDayWindForceNo(Integer dayWindForceNo) {
        this.dayWindForceNo = dayWindForceNo;
    }

    public Integer getNightWindForceNo() {
        return nightWindForceNo;
    }

    public void setNightWindForceNo(Integer nightWindForceNo) {
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
