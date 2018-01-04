package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017/12/28.
 */

public class TurnoverBean {

    /**
     * coal_count : 煤炭成交量
     * coal_price : 煤炭成交金额
     * steel_count : 有色金属成交量
     * steel_price : 有色金属成交金额
     */

    @SerializedName("coal_count")
    private String coalCount;
    @SerializedName("coal_price")
    private String coalPrice;
    @SerializedName("steel_count")
    private String steelCount;
    @SerializedName("steel_price")
    private String steelPrice;

    public String getCoalCount() {
        return coalCount;
    }

    public void setCoalCount(String coalCount) {
        this.coalCount = coalCount;
    }

    public String getCoalPrice() {
        return coalPrice;
    }

    public void setCoalPrice(String coalPrice) {
        this.coalPrice = coalPrice;
    }

    public String getSteelCount() {
        return steelCount;
    }

    public void setSteelCount(String steelCount) {
        this.steelCount = steelCount;
    }

    public String getSteelPrice() {
        return steelPrice;
    }

    public void setSteelPrice(String steelPrice) {
        this.steelPrice = steelPrice;
    }
}
