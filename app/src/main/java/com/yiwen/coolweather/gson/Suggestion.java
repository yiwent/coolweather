package com.yiwen.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-04
 * Time: 10:06
 * FIXME
 */
public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;
    public class Comfort{
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
