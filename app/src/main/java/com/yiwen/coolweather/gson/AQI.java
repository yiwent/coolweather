package com.yiwen.coolweather.gson;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-04
 * Time: 09:59
 * FIXME
 */
public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;

    }
}
