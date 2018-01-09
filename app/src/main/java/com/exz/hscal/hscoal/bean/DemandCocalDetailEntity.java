package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/9.
 */

public class DemandCocalDetailEntity {

    /**
     * name : 品名
     * coalVarietyName : 煤炭品种
     * purchaseQuantity : 求购数量（吨）
     * fixedCarbon : 固定碳（煤种：焦炭/焦粉/焦粒、动力煤）
     * calorificValue : 发热量（煤种：焦炭/焦粉/焦粒）
     * ashSpecification : 灰份（煤种：焦炭/焦粉/焦粒、炼焦煤、动力煤）
     * volatiles : 挥发份（煤种：焦炭/焦粉/焦粒、炼焦煤）
     * inherentMoisture : 内水（煤种：焦炭/焦粉/焦粒、动力煤）
     * totalSulfurContent : 全硫份（煤种：焦炭/焦粉/焦粒、炼焦煤）
     * bond : 粘结（煤种：炼焦煤）
     * Y_Value : Y值（煤种：炼焦煤、动力煤）
     * lithofacies : 岩相（煤种：炼焦煤）
     * CSR : CSR（煤种：炼焦煤）
     * lowerCalorificValue : 低位热值（煤种：动力煤）
     * airDrySulfur : 空干基硫分（煤种：动力煤）
     * airDryRadicalVolatiles : 空干基挥发分（煤种：动力煤）
     * provinceCity : 江苏徐州淮海西路
     * plannedDeliveryTime : 2017-12-30
     * remark : 备注
     * provinceId : 交货省份id
     * cityId : 交货城市id
     * placeDelivery : 交货地
     */

    @SerializedName("name")
    private String name;
    @SerializedName("coalVarietyName")
    private String coalVarietyName;
    @SerializedName("purchaseQuantity")
    private String purchaseQuantity;
    @SerializedName("fixedCarbon")
    private String fixedCarbon;
    @SerializedName("calorificValue")
    private String calorificValue;
    @SerializedName("ashSpecification")
    private String ashSpecification;
    @SerializedName("volatiles")
    private String volatiles;
    @SerializedName("inherentMoisture")
    private String inherentMoisture;
    @SerializedName("totalSulfurContent")
    private String totalSulfurContent;
    @SerializedName("bond")
    private String bond;
    @SerializedName("Y_Value")
    private String YValue;
    @SerializedName("lithofacies")
    private String lithofacies;
    @SerializedName("CSR")
    private String CSR;
    @SerializedName("lowerCalorificValue")
    private String lowerCalorificValue;
    @SerializedName("airDrySulfur")
    private String airDrySulfur;
    @SerializedName("airDryRadicalVolatiles")
    private String airDryRadicalVolatiles;
    @SerializedName("provinceCity")
    private String provinceCity;
    @SerializedName("plannedDeliveryTime")
    private String plannedDeliveryTime;
    @SerializedName("remark")
    private String remark;
    @SerializedName("provinceId")
    private String provinceId;
    @SerializedName("cityId")
    private String cityId;
    @SerializedName("placeDelivery")
    private String placeDelivery;

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

    public String getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(String purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
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

    public String getAshSpecification() {
        return ashSpecification;
    }

    public void setAshSpecification(String ashSpecification) {
        this.ashSpecification = ashSpecification;
    }

    public String getVolatiles() {
        return volatiles;
    }

    public void setVolatiles(String volatiles) {
        this.volatiles = volatiles;
    }

    public String getInherentMoisture() {
        return inherentMoisture;
    }

    public void setInherentMoisture(String inherentMoisture) {
        this.inherentMoisture = inherentMoisture;
    }

    public String getTotalSulfurContent() {
        return totalSulfurContent;
    }

    public void setTotalSulfurContent(String totalSulfurContent) {
        this.totalSulfurContent = totalSulfurContent;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getYValue() {
        return YValue;
    }

    public void setYValue(String YValue) {
        this.YValue = YValue;
    }

    public String getLithofacies() {
        return lithofacies;
    }

    public void setLithofacies(String lithofacies) {
        this.lithofacies = lithofacies;
    }

    public String getCSR() {
        return CSR;
    }

    public void setCSR(String CSR) {
        this.CSR = CSR;
    }

    public String getLowerCalorificValue() {
        return lowerCalorificValue;
    }

    public void setLowerCalorificValue(String lowerCalorificValue) {
        this.lowerCalorificValue = lowerCalorificValue;
    }

    public String getAirDrySulfur() {
        return airDrySulfur;
    }

    public void setAirDrySulfur(String airDrySulfur) {
        this.airDrySulfur = airDrySulfur;
    }

    public String getAirDryRadicalVolatiles() {
        return airDryRadicalVolatiles;
    }

    public void setAirDryRadicalVolatiles(String airDryRadicalVolatiles) {
        this.airDryRadicalVolatiles = airDryRadicalVolatiles;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPlaceDelivery() {
        return placeDelivery;
    }

    public void setPlaceDelivery(String placeDelivery) {
        this.placeDelivery = placeDelivery;
    }
}
