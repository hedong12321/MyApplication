package com.example.dongdong_weather.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dongdong_weather.model.AreaCode;

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
}
