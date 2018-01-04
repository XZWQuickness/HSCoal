package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/3.
 */

public class ConfirmOrderBean {

    /**
     * name : 商品名称
     * image : http://123.png
     * price : 518元/吨
     * residualCount : 剩余数量,用来比较下单最大值
     */

    private String name;
    private String image;
    private String price;
    private String residualCount;

    private String buyOrderId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResidualCount() {
        return residualCount;
    }

    public void setResidualCount(String residualCount) {
        this.residualCount = residualCount;
    }

    public String getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(String buyOrderId) {
        this.buyOrderId = buyOrderId;
    }
}
