package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/10.
 */

public class CocalOrderDetaileBean {

    /**
     * name : 品名
     * coalVarietyName : 煤炭品种
     * place : 产地
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
     * ashFusionPoint : 灰熔点（煤种：动力煤）
     * airDryRadicalVolatiles : 空干基挥发分（煤种：动力煤）
     * baseVolatiles : 收到基挥发分（煤种：动力煤）
     * totalMoisture : 全水分（煤种：动力煤）
     * G_Value : G值（煤种：动力煤）
     * paymentModeName : 现金汇款
     * inspectonBody : 检验机构
     * inspectonBody_Img : 检验机构_报告图片
     * deliveryTime : 2017-01-01 至 2017-05-01
     * deliveryWayName : 到场自提
     * provinceCity : 广西钦州
     * remark : 备注
     * price : 889.0元/吨
     * count : 100
     * payMoney : 实付款
     * consignee : 收货人
     * mobile : 联系电话
     * address : 详细地址
     * createDate : 2017-12-08 16:46
     */

    @SerializedName("name")
    private String name;
    @SerializedName("coalVarietyName")
    private String coalVarietyName;
    @SerializedName("place")
    private String place;
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
    @SerializedName("ashFusionPoint")
    private String ashFusionPoint;
    @SerializedName("airDryRadicalVolatiles")
    private String airDryRadicalVolatiles;
    @SerializedName("baseVolatiles")
    private String baseVolatiles;
    @SerializedName("totalMoisture")
    private String totalMoisture;
    @SerializedName("G_Value")
    private String GValue;
    @SerializedName("paymentModeName")
    private String paymentModeName;
    @SerializedName("inspectonBody")
    private String inspectonBody;
    @SerializedName("inspectonBody_Img")
    private String inspectonBodyImg;
    @SerializedName("deliveryTime")
    private String deliveryTime;
    @SerializedName("deliveryWayName")
    private String deliveryWayName;
    @SerializedName("provinceCity")
    private String provinceCity;
    @SerializedName("remark")
    private String remark;
    @SerializedName("price")
    private String price;
    @SerializedName("count")
    private String count;
    @SerializedName("payMoney")
    private String payMoney;
    @SerializedName("consignee")
    private String consignee;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("address")
    private String address;
    @SerializedName("createDate")
    private String createDate;

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public String getAshFusionPoint() {
        return ashFusionPoint;
    }

    public void setAshFusionPoint(String ashFusionPoint) {
        this.ashFusionPoint = ashFusionPoint;
    }

    public String getAirDryRadicalVolatiles() {
        return airDryRadicalVolatiles;
    }

    public void setAirDryRadicalVolatiles(String airDryRadicalVolatiles) {
        this.airDryRadicalVolatiles = airDryRadicalVolatiles;
    }

    public String getBaseVolatiles() {
        return baseVolatiles;
    }

    public void setBaseVolatiles(String baseVolatiles) {
        this.baseVolatiles = baseVolatiles;
    }

    public String getTotalMoisture() {
        return totalMoisture;
    }

    public void setTotalMoisture(String totalMoisture) {
        this.totalMoisture = totalMoisture;
    }

    public String getGValue() {
        return GValue;
    }

    public void setGValue(String GValue) {
        this.GValue = GValue;
    }

    public String getPaymentModeName() {
        return paymentModeName;
    }

    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }

    public String getInspectonBody() {
        return inspectonBody;
    }

    public void setInspectonBody(String inspectonBody) {
        this.inspectonBody = inspectonBody;
    }

    public String getInspectonBodyImg() {
        return inspectonBodyImg;
    }

    public void setInspectonBodyImg(String inspectonBodyImg) {
        this.inspectonBodyImg = inspectonBodyImg;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryWayName() {
        return deliveryWayName;
    }

    public void setDeliveryWayName(String deliveryWayName) {
        this.deliveryWayName = deliveryWayName;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
