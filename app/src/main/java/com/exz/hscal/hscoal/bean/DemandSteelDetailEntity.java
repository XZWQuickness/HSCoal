package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/9.
 */

public class DemandSteelDetailEntity {


    /**
     * name : 品名
     * className : 分类名称
     * purchaseQuantity : 求购数量（吨）
     * specification : 规格
     * materialQuality : 材质
     * weight : 件重
     * provinceCity : 江苏徐州淮海西路
     * plannedDeliveryTime : 2017-12-30
     * remark : 备注
     * provinceId : 交货省份id
     * cityId : 交货城市id
     * placeDelivery : 交货地
     */

    @SerializedName("name")
    private String name;
    @SerializedName("className")
    private String className;
    @SerializedName("purchaseQuantity")
    private String purchaseQuantity;
    @SerializedName("specification")
    private String specification;
    @SerializedName("materialQuality")
    private String materialQuality;
    @SerializedName("weight")
    private String weight;
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

    private String deliveryWayName;
    private String contactName;
    private String contactMobile;

    public String getDeliveryWayName() {
        return deliveryWayName;
    }

    public void setDeliveryWayName(String deliveryWayName) {
        this.deliveryWayName = deliveryWayName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(String purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getMaterialQuality() {
        return materialQuality;
    }

    public void setMaterialQuality(String materialQuality) {
        this.materialQuality = materialQuality;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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
