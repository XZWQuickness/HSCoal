package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/10.
 */

public class OrderBean {

    /**
     * orderId : 订单号
     * name : 洗煤精（动力煤）
     * image : http://123.png
     * lowerCalorificValue : 低位热值（煤种：动力煤）
     * ashSpecification : 灰份（煤种：炼焦煤）
     * totalSulfurContent : 全硫份（煤种：炼焦煤）
     * fixedCarbon : 固定碳（煤种：焦炭/焦粉/焦粒）
     * calorificValue : 发热量（煤种：焦炭/焦粉/焦粒）
     * price : 单价
     * count : 购买数量
     * payMoney : 实付款
     * createDate : 2017-12-08 16:46
     * state : 0
     */

    @SerializedName("orderId")
    private String orderId;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("lowerCalorificValue")
    private String lowerCalorificValue;
    @SerializedName("ashSpecification")
    private String ashSpecification;
    @SerializedName("totalSulfurContent")
    private String totalSulfurContent;
    @SerializedName("fixedCarbon")
    private String fixedCarbon;
    @SerializedName("calorificValue")
    private String calorificValue;
    @SerializedName("price")
    private String price;
    @SerializedName("count")
    private String count;
    @SerializedName("payMoney")
    private String payMoney;
    @SerializedName("createDate")
    private String createDate;
    @SerializedName("state")
    private String state;
    private String coalVarietyName;

    public String getCoalVarietyName() {
        return coalVarietyName;
    }

    public void setCoalVarietyName(String coalVarietyName) {
        this.coalVarietyName = coalVarietyName;
    }

    /**
     * description : 材质/规格
     */

    @SerializedName("description")
    private String description;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public String getLowerCalorificValue() {
        return lowerCalorificValue;
    }

    public void setLowerCalorificValue(String lowerCalorificValue) {
        this.lowerCalorificValue = lowerCalorificValue;
    }

    public String getAshSpecification() {
        return ashSpecification;
    }

    public void setAshSpecification(String ashSpecification) {
        this.ashSpecification = ashSpecification;
    }

    public String getTotalSulfurContent() {
        return totalSulfurContent;
    }

    public void setTotalSulfurContent(String totalSulfurContent) {
        this.totalSulfurContent = totalSulfurContent;
    }

    public String getFixedCarbon() {
        return fixedCarbon;
    }

    public void setFixedCarbon(String fixedCarbon) {
        this.fixedCarbon = fixedCarbon;
    }

    public String getCalorificValue() {
        return calorificValue;
    }

    public void setCalorificValue(String calorificValue) {
        this.calorificValue = calorificValue;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
