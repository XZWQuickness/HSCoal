package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/10.
 */

public class SteelOrderInfoBean {

    /**
     * name : 品名
     * className : 分类名称
     * steelworks : 钢厂
     * specification : 规格
     * materialQuality : 材质
     * warehouse : 仓库
     * weight : 件重
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
    @SerializedName("className")
    private String className;
    @SerializedName("steelworks")
    private String steelworks;
    @SerializedName("specification")
    private String specification;
    @SerializedName("materialQuality")
    private String materialQuality;
    @SerializedName("warehouse")
    private String warehouse;
    @SerializedName("weight")
    private String weight;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSteelworks() {
        return steelworks;
    }

    public void setSteelworks(String steelworks) {
        this.steelworks = steelworks;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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
