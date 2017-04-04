package com.yiwen.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-04
 * Time: 10:13
 * FIXME
 */
public class Forecast {
    public String data;
    @SerializedName("tmp")
    public Temperature temperature;
    @SerializedName("cond")
    public More more;
    public class Temperature{
        public String max;
        public String min;
    }
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
