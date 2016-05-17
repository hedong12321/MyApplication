package com.example.dongdong_weather.model;

/**
 * Created by dong.he on 2015/8/11.
 */
public class WeatherHistory {

    private Integer pkId;
    private String areaId;
    private String forecastDate;
    private String dayWeatherNo;
    private String nightWeatherNo;
    private String dayTemperature;
    private String nightTemperature;
    private String dayWindDirNo;
    private String nightWindDirNo;
    private String dayWindForceNo;
    private String nightWindForceNo;
    private String sunriseSunset;
    private String publishTime;

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

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
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

	public String getDayWeatherNo() {
		return dayWeatherNo;
	}

	public void setDayWeatherNo(String dayWeatherNo) {
		this.dayWeatherNo = dayWeatherNo;
	}

	public String getNightWeatherNo() {
		return nightWeatherNo;
	}

	public void setNightWeatherNo(String nightWeatherNo) {
		this.nightWeatherNo = nightWeatherNo;
	}

	public String getDayTemperature() {
		return dayTemperature;
	}

	public void setDayTemperature(String dayTemperature) {
		this.dayTemperature = dayTemperature;
	}

	public String getNightTemperature() {
		return nightTemperature;
	}

	public void setNightTemperature(String nightTemperature) {
		this.nightTemperature = nightTemperature;
	}

	public String getDayWindDirNo() {
		return dayWindDirNo;
	}

	public void setDayWindDirNo(String dayWindDirNo) {
		this.dayWindDirNo = dayWindDirNo;
	}

	public String getNightWindDirNo() {
		return nightWindDirNo;
	}

	public void setNightWindDirNo(String nightWindDirNo) {
		this.nightWindDirNo = nightWindDirNo;
	}

	public String getDayWindForceNo() {
		return dayWindForceNo;
	}

	public void setDayWindForceNo(String dayWindForceNo) {
		this.dayWindForceNo = dayWindForceNo;
	}

	public String getNightWindForceNo() {
		return nightWindForceNo;
	}

	public void setNightWindForceNo(String nightWindForceNo) {
		this.nightWindForceNo = nightWindForceNo;
	}
}
