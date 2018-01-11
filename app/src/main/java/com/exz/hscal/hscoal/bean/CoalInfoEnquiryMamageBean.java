package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/11.
 */

public class CoalInfoEnquiryMamageBean {

    /**
     * state : 0
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
     * provinceCity : 江苏徐州
     * placeDelivery : 淮海西路120号
     * plannedDeliveryTime : 2017-12-30
     * contactName : 徐先生
     * contactMobile : 15895226665
     * remark : 备注
     * quoteCount : 收到报价的数量
     * quoteContactName : 报价联系人
     * quoteContactPhone : 报价联系方式
     */

    @SerializedName("state")
    private String state;
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
    @SerializedName("placeDelivery")
    private String placeDelivery;
    @SerializedName("plannedDeliveryTime")
    private String plannedDeliveryTime;
    @SerializedName("contactName")
    private String contactName;
    @SerializedName("contactMobile")
    private String contactMobile;
    @SerializedName("remark")
    private String remark;
    @SerializedName("quoteCount")
    private String quoteCount;
    @SerializedName("quoteContactName")
    private String quoteContactName;
    @SerializedName("quoteContactPhone")
    private String quoteContactPhone;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getPlaceDelivery() {
        return placeDelivery;
    }

    public void setPlaceDelivery(String placeDelivery) {
        this.placeDelivery = placeDelivery;
    }

    public String getPlannedDeliveryTime() {
        return plannedDeliveryTime;
    }

    public void setPlannedDeliveryTime(String plannedDeliveryTime) {
        this.plannedDeliveryTime = plannedDeliveryTime;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(String quoteCount) {
        this.quoteCount = quoteCount;
    }

    public String getQuoteContactName() {
        return quoteContactName;
    }

    public void setQuoteContactName(String quoteContactName) {
        this.quoteContactName = quoteContactName;
    }

    public String getQuoteContactPhone() {
        return quoteContactPhone;
    }

    public void setQuoteContactPhone(String quoteContactPhone) {
        this.quoteContactPhone = quoteContactPhone;
    }
}
