package com.derekzy.dexweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.derekzy.dexweather.db.DexWeatherDB;
import com.derekzy.dexweather.model.City;
import com.derekzy.dexweather.model.County;
import com.derekzy.dexweather.model.Province;

/**
 * Created by derekzy on 2016/2/16.
 */
public class Utility {

    public synchronized static boolean handleProvincesResponse(DexWeatherDB dexWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            Log.e("********", "response is "+response+"allProvinces is "+allProvinces);
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    Log.e("^^^^^", "array[0] is "+array[0]+",array[1] is "+array[1]);

                    dexWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false; //?
    }

    public static boolean handleCitiesResponse(DexWeatherDB dexWeatherDB, String response, int provinceId) {
        //, int provinceId
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            Log.e("********", "response is "+response+"allCities is "+allCities);
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    Log.e("^^^^^", "provinceId is "+ provinceId);
                    dexWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCountiesResponse(DexWeatherDB dexWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null &&allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);

                    dexWeatherDB.saveCounty(county);
                }
                return true; //
            }
        }
        return false;
    }
}
