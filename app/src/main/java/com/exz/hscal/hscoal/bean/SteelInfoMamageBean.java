package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/11.
 */

public class SteelInfoMamageBean {

    /**
     * state : 0
     * name : 品名
     * className : 分类名称
     * purchaseQuantity : 求购数量（吨）
     * specification : 规格
     * materialQuality : 材质
     * weight : 件重
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
