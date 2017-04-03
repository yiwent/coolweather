package com.yiwen.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-03
 * Time: 22:10
 * FIXME
 */
public class City extends DataSupport{
    private int id;
    private String ciytName;
    private int cityCode;
    private int proviceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCiytName() {
        return ciytName;
    }

    public void setCiytName(String ciytName) {
        this.ciytName = ciytName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProviceId() {
        return proviceId;
    }

    public void setProviceId(int proviceId) {
        this.proviceId = proviceId;
    }
}
