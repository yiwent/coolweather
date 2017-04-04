package com.yiwen.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-04
 * Time: 10:02
 * FIXME
 */
public class Now {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More   more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
