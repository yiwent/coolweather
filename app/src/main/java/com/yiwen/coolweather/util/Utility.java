package com.yiwen.coolweather.util;

import android.text.TextUtils;

import com.yiwen.coolweather.db.City;
import com.yiwen.coolweather.db.County;
import com.yiwen.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-03
 * Time: 22:33
 * FIXME
 */
public class Utility {
    /**
     * 解析省数据
     *
     * @param response
     * @return
     */
    public static boolean handProviceRespose(String response) {
        if (!TextUtils.isEmpty(response)) {

            try {
                JSONArray allProvice = new JSONArray(response);
                for (int i = 0; i < allProvice.length(); i++) {
                    JSONObject proviceObject = allProvice.getJSONObject(i);
                    Province province = new Province();
                    province.setProviceNane(proviceObject.getString("name"));
                    province.setProviceCode(proviceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 返回市的数据
     * @param response
     * @param proviceId
     * @return
     */
    public static boolean handCityRespose(String response, int proviceId) {
        if (!TextUtils.isEmpty(response)) {

            try {
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0; i < allCounty.length(); i++) {
                    JSONObject CityObject = allCounty.getJSONObject(i);
                    City city = new City();
                    city.setCiytName(CityObject.getString("name"));
                    city.setCityCode(CityObject.getInt("weather_id"));
                    city.setProviceId(proviceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析县返回数据
     *
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handCountyRespose(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {

            try {
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0; i < allCounty.length(); i++) {
                    JSONObject CountyObject = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(CountyObject.getString("name"));
                    county.setWeatherId(CountyObject.getString("weather_id"));
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
