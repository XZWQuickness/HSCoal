package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/9.
 */

public class DemandBean {


    /**
     * id : 编号
     * name : 品名
     * coalVarietyName : 煤种
     * lowerCalorificValue : 低位热值（煤种：动力煤）
     * ashSpecification : 灰份（煤种：炼焦煤）
     * totalSulfurContent : 全硫份（煤种：炼焦煤）
     * fixedCarbon : 固定碳（煤种：焦炭/焦粉/焦粒）
     * calorificValue : 发热量（煤种：焦炭/焦粉/焦粒）
     * purchaseQuantity : 求购数量（吨）
     * provinceCity : 交货地
     * plannedDeliveryTime : 计划收货时间
     */

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("coalVarietyName")
    private String coalVarietyName;
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
    @SerializedName("purchaseQuantity")
    private String purchaseQuantity;
    @SerializedName("provinceCity")
    private String provinceCity;
    @SerializedName("plannedDeliveryTime")
    private String plannedDeliveryTime;
    /**
     * description : 分类/材质/规格
     */

    @SerializedName("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoalVarietyName() {
        return coalVarietyName;
    }

    public void setCoalVarietyName(String coalVarietyName) {
        this.coalVarietyName = coalVarietyName;
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

    public String getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(String purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getPlannedDeliveryTime() {
        return plannedDeliveryTime;
    }

    public void setPlannedDeliveryTime(String plannedDeliveryTime) {
        this.plannedDeliveryTime = plannedDeliveryTime;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
