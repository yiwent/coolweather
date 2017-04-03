package com.yiwen.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-03
 * Time: 22:07
 * FIXME
 */
public class Province extends DataSupport {
    private int    id;
    private String proviceNane;
    private int    proviceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProviceNane() {
        return proviceNane;
    }

    public void setProviceNane(String proviceNane) {
        this.proviceNane = proviceNane;
    }

    public int getProviceCode() {
        return proviceCode;
    }

    public void setProviceCode(int proviceCode) {
        this.proviceCode = proviceCode;
    }
}
