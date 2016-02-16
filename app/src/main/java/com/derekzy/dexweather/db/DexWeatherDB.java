package com.derekzy.dexweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.derekzy.dexweather.model.City;
import com.derekzy.dexweather.model.County;
import com.derekzy.dexweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derekzy on 2016/2/16.
 */
public class DexWeatherDB {
    /**
     *  原本我们需要在Activity中实例化DatabaseOpenHelper，然后把数据put进去，我们在这里进行封装方法
     */

    public static final String DB_NAME = "dex_weather";

    public static final int VERSION = 2;
    private static DexWeatherDB dexWeatherDB;
    private SQLiteDatabase db;

    private DexWeatherDB(Context context) {
        DexWeatherOpenHelper dbHelper = new DexWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static DexWeatherDB getInstance(Context context) {
        if(dexWeatherDB == null) {
            dexWeatherDB = new DexWeatherDB(context);
        }
        return dexWeatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    public List<Province> loadProvinces() {
        List<Province> provinceList = new ArrayList<>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinceList.add(province);

                Log.e("*******", "次数");
            } while (cursor.moveToNext());
        }
        else if (cursor != null) {
            cursor.close();
        }
        return provinceList;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    public List<City> loadCities(int provinceId) {
        List<City> cityList = new ArrayList<>();
        Cursor cursor = db.query("City", null, "province_id = ?" , new String[] {String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                cityList.add(city);
            } while (cursor.moveToNext());
        }
        else if (cursor != null) {
            cursor.close();
        }
        return cityList;
    }

    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("City_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    public List<County> loadCounties(int cityId) {
        List<County> countyList = new ArrayList<>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                countyList.add(county);
            } while (cursor.moveToNext());
        } else if (cursor != null) {
            cursor.close();
        }
        return countyList;
    }
}
