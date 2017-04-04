package com.yiwen.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-04
 * Time: 09:49
 * FIXME
 */
public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
