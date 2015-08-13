package com.example.dongdong_weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dongdong_weather.model.AreaCode;
import com.example.dongdong_weather.model.AreaExtInfo;
import com.example.dongdong_weather.model.WeatherCode;
import com.example.dongdong_weather.model.WeatherHistory;
import com.example.dongdong_weather.model.WindDirection;
import com.example.dongdong_weather.model.WindForce;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong.he on 2015/7/29.
 */
public class WeatherDB {

    /**
     * 数据库名称
     */
    public static final String DB_NAME = "dongdong_weather.db";

    private static WeatherDB weatherDB;
    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private WeatherDB(Context context) {
        WeatherOpenHelper dbhelper = new WeatherOpenHelper(context, DB_NAME, null, 1);
        db = dbhelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例。
     */
    public synchronized static WeatherDB getInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }

        return weatherDB;
    }

    /**
     * 查询城市
     */
    public List<AreaCode> loadCities() {
        List<AreaCode> list = new ArrayList<AreaCode>();
        Cursor cursor = db.query("area_code", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                AreaCode areaCode = new AreaCode();
                areaCode.setPkId(cursor.getInt(cursor.getColumnIndex("pk_id")));
                areaCode.setAreaId(cursor.getInt(cursor.getColumnIndex("area_id")));
                areaCode.setNameEn(cursor.getString(cursor.getColumnIndex("name_en")));
                areaCode.setNameZh(cursor.getString(cursor.getColumnIndex("name_zh")));
                areaCode.setDistrictEn(cursor.getString(cursor.getColumnIndex("district_en")));
                areaCode.setDistrictZh(cursor.getString(cursor.getColumnIndex("district_zh")));
                areaCode.setProvEn(cursor.getString(cursor.getColumnIndex("prov_en")));
                areaCode.setProvZh(cursor.getString(cursor.getColumnIndex("prov_zh")));
                areaCode.setNationEn(cursor.getString(cursor.getColumnIndex("nation_en")));
                areaCode.setNationZh(cursor.getString(cursor.getColumnIndex("nation_zh")));
                areaCode.setSortLetters(cursor.getString(cursor.getColumnIndex("name_en")).substring(0, 1).toUpperCase());
                list.add(areaCode);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 根据areaId查询城市
     */
    public AreaCode getCityByAreaId(String areaId) {
        Cursor cursor = db.query("area_code", null, "area_id = ?", new String[]{areaId}, null, null, null);
        if (cursor.moveToFirst()) {
            AreaCode areaCode = new AreaCode();
            areaCode.setPkId(cursor.getInt(cursor.getColumnIndex("pk_id")));
            areaCode.setAreaId(cursor.getInt(cursor.getColumnIndex("area_id")));
            areaCode.setNameEn(cursor.getString(cursor.getColumnIndex("name_en")));
            areaCode.setNameZh(cursor.getString(cursor.getColumnIndex("name_zh")));
            areaCode.setDistrictEn(cursor.getString(cursor.getColumnIndex("district_en")));
            areaCode.setDistrictZh(cursor.getString(cursor.getColumnIndex("district_zh")));
            areaCode.setProvEn(cursor.getString(cursor.getColumnIndex("prov_en")));
            areaCode.setProvZh(cursor.getString(cursor.getColumnIndex("prov_zh")));
            areaCode.setNationEn(cursor.getString(cursor.getColumnIndex("nation_en")));
            areaCode.setNationZh(cursor.getString(cursor.getColumnIndex("nation_zh")));
            areaCode.setSortLetters(cursor.getString(cursor.getColumnIndex("name_en")).substring(0, 1).toUpperCase());

            return areaCode;
        }
        return null;
    }

    /**
     * 根据areaId查询城市扩展信息
     */
    public AreaExtInfo getAreaExtInfoByAreaId(String areaId) {

        Cursor cursor = db.query("area_ext_info", null, "area_id = ?", new String[]{areaId}, null, null, null);
        if (cursor.moveToFirst()) {
            AreaExtInfo extInfo = new AreaExtInfo();
            extInfo.setPkId(cursor.getInt(cursor.getColumnIndex("pk_id")));
            extInfo.setAreaId(cursor.getInt(cursor.getColumnIndex("area_id")));
            extInfo.setCityOrder(cursor.getInt(cursor.getColumnIndex("city_order")));
            extInfo.setCityAreaCode(cursor.getString(cursor.getColumnIndex("city_area_code")));
            extInfo.setPostcode(cursor.getString(cursor.getColumnIndex("postcode")));
            extInfo.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
            extInfo.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
            extInfo.setAltitude(cursor.getFloat(cursor.getColumnIndex("altitude")));
            extInfo.setRadarNo(cursor.getString(cursor.getColumnIndex("radar_no")));
            extInfo.setTimeZone(cursor.getString(cursor.getColumnIndex("time_zone")));

            return extInfo;
        }

        return null;
    }

    /**
     * 添加城市扩展信息
     */
    public void addAreaExtInfo(AreaExtInfo extInfo) {

        ContentValues values = new ContentValues();
        values.put("area_id", extInfo.getAreaId());
        values.put("city_order", extInfo.getCityOrder());
        values.put("city_area_code", extInfo.getCityAreaCode());
        values.put("postcode", extInfo.getPostcode());
        values.put("latitude", extInfo.getLatitude());
        values.put("longitude", extInfo.getLongitude());
        values.put("altitude", extInfo.getAltitude());
        values.put("radar_no", extInfo.getRadarNo());
        values.put("time_zone", extInfo.getTimeZone());

        db.insert("area_ext_info", null, values);
    }

    /**
     * 修改城市扩展信息
     */
    public void updateAreaExtInfo(AreaExtInfo extInfo) {

        ContentValues values = new ContentValues();
        //values.put("area_id", extInfo.getAreaId());
        values.put("city_order", extInfo.getCityOrder());
        values.put("city_area_code", extInfo.getCityAreaCode());
        values.put("postcode", extInfo.getPostcode());
        values.put("latitude", extInfo.getLatitude());
        values.put("longitude", extInfo.getLongitude());
        values.put("altitude", extInfo.getAltitude());
        values.put("radar_no", extInfo.getRadarNo());
        values.put("time_zone", extInfo.getTimeZone());

        db.update("area_ext_info", values, "area_id = ?", new String[]{String.valueOf(extInfo.getAreaId())});
    }

    /**
     * 根据areaId和预报日期查询已经保存的天气信息
     */
    public WeatherHistory getWeatherHistoryByAreaIdAndDate(String areaId, String forecast) {

        Cursor cursor = db.query("weather_history", null, "area_id = ? and forecast_date = ?",
                new String[]{areaId, forecast}, null, null, null);
        if (cursor.moveToFirst()) {
            WeatherHistory history = new WeatherHistory();
            history.setPkId(cursor.getInt(cursor.getColumnIndex("pk_id")));
            history.setAreaId(cursor.getInt(cursor.getColumnIndex("area_id")));
            history.setForecastDate(cursor.getString(cursor.getColumnIndex("forecast_date")));
            history.setDayWeatherNo(cursor.getInt(cursor.getColumnIndex("day_weather_no")));
            history.setNightWeatherNo(cursor.getInt(cursor.getColumnIndex("night_weather_no")));
            history.setDayTemperature(cursor.getInt(cursor.getColumnIndex("day_temperature")));
            history.setNightTemperature(cursor.getInt(cursor.getColumnIndex("night_temperature")));
            history.setDayWindDirNo(cursor.getInt(cursor.getColumnIndex("day_wind_dir_no")));
            history.setNightWindDirNo(cursor.getInt(cursor.getColumnIndex("night_wind_dir_no")));
            history.setDayWindForceNo(cursor.getInt(cursor.getColumnIndex("day_wind_force_no")));
            history.setNightWindForceNo(cursor.getInt(cursor.getColumnIndex("night_wind_force_no")));
            history.setSunriseSunset(cursor.getString(cursor.getColumnIndex("sunrise_sunset")));
            history.setPublishTime(cursor.getString(cursor.getColumnIndex("publish_time")));

            return history;
        }

        return null;
    }

    /**
     * 保存天气信息
     */
    public void saveWeatherHistory(WeatherHistory history) {

        ContentValues values = new ContentValues();
        values.put("area_id", history.getAreaId());
        values.put("forecast_date", history.getForecastDate());
        values.put("day_weather_no", history.getDayWeatherNo());
        values.put("night_weather_no", history.getNightWeatherNo());
        values.put("day_temperature", history.getDayTemperature());
        values.put("night_temperature", history.getNightTemperature());
        values.put("day_wind_dir_no", history.getDayWindDirNo());
        values.put("night_wind_dir_no", history.getNightWindDirNo());
        values.put("day_wind_force_no", history.getDayWindForceNo());
        values.put("night_wind_force_no", history.getNightWindForceNo());
        values.put("sunrise_sunset", history.getSunriseSunset());
        values.put("publish_time", history.getPublishTime());

        db.insert("weather_history", null, values);
    }

    /**
     * 更新天气信息
     */
    public void updateWeatherHistory(WeatherHistory history) {

        ContentValues values = new ContentValues();
        //values.put("area_id", history.getAreaId());
        //values.put("forecast_date", history.getForecastDate());
        values.put("day_weather_no", history.getDayWeatherNo());
        values.put("night_weather_no", history.getNightWeatherNo());
        values.put("day_temperature", history.getDayTemperature());
        values.put("night_temperature", history.getNightTemperature());
        values.put("day_wind_dir_no", history.getDayWindDirNo());
        values.put("night_wind_dir_no", history.getNightWindDirNo());
        values.put("day_wind_force_no", history.getDayWindForceNo());
        values.put("night_wind_force_no", history.getNightWindForceNo());
        values.put("sunrise_sunset", history.getSunriseSunset());
        values.put("publish_time", history.getPublishTime());

        db.update("weather_history", values, "area_id = ? and forecast_date = ?",
                new String[]{String.valueOf(history.getAreaId()), history.getForecastDate()});
    }

    /**
     * 根据天气编号查询天气信息
     */
    public WeatherCode getWeatherCodeByWeatherNo(String weatherNo) {
        Cursor cursor = db.query("weather_code", null, "weather_no = ?", new String[]{weatherNo}, null, null, null);
        if (cursor.moveToFirst()) {
            WeatherCode weather = new WeatherCode();
            weather.setPkId(cursor.getInt(cursor.getColumnIndex("pk_id")));
            weather.setWeatherNo(cursor.getInt(cursor.getColumnIndex("weather_no")));
            weather.setNameZh(cursor.getString(cursor.getColumnIndex("name_zh")));
            weather.setNameEn(cursor.getString(cursor.getColumnIndex("name_en")));

            return weather;
        }
        return null;
    }

    /**
     * 根据风向编号查询风向信息
     */
    public WindDirection getWindDirectionByDirectionNo(String directionNo) {
        Cursor cursor = db.query("wind_direction", null, "direction_no = ?", new String[]{directionNo}, null, null, null);
        if (cursor.moveToFirst()) {
            WindDirection direction = new WindDirection();
            direction.setPkId(cursor.getInt(cursor.getColumnIndex("pk_id")));
            direction.setDirectionNo(cursor.getInt(cursor.getColumnIndex("direction_no")));
            direction.setNameZh(cursor.getString(cursor.getColumnIndex("name_zh")));
            direction.setNameEn(cursor.getString(cursor.getColumnIndex("name_en")));

            return direction;
        }
        return null;
    }

    /**
     * 根据风力编号查询风力信息
     */
    public WindForce getWindForceByDirectionNo(String forceNo) {
        Cursor cursor = db.query("wind_force", null, "force_no = ?", new String[]{forceNo}, null, null, null);
        if (cursor.moveToFirst()) {
            WindForce force = new WindForce();
            force.setPkId(cursor.getInt(cursor.getColumnIndex("pk_id")));
            force.setForceNo(cursor.getInt(cursor.getColumnIndex("force_no")));
            force.setNameZh(cursor.getString(cursor.getColumnIndex("name_zh")));
            force.setNameEn(cursor.getString(cursor.getColumnIndex("name_en")));

            return force;
        }
        return null;
    }
}
