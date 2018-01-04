package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/2.
 */

public class SeekSteelDetailentity {

    /**
     * name : 品名
     * className : 热卷
     * image : http://hs.xzsem.cn123
     * steelworks : 钢厂
     * specification : 规格
     * materialQuality : 材质
     * warehouse : 仓库
     * remark :
     * provinceCity : 北京市 北京市
     * deliveryTime : 2017-01-01,2017-05-01
     * deliveryWayName : 物流配送
     * isChoiceDelivery : 1
     * weight : 1.000
     * QTY : 999
     * price : 5000.0
     * paymentModeName : 现金汇款
     * inspectonBody :
     * inspectonBody_Img : http://hs.xzsem.cn
     * povinceId : 10000
     * cityId : 110000
     * state : 1
     */

    @SerializedName("name")
    private String name;
    @SerializedName("className")
    private String className;
    @SerializedName("image")
    private String image;
    @SerializedName("steelworks")
    private String steelworks;
    @SerializedName("specification")
    private String specification;
    @SerializedName("materialQuality")
    private String materialQuality;
    @SerializedName("warehouse")
    private String warehouse;
    @SerializedName("remark")
    private String remark;
    @SerializedName("provinceCity")
    private String provinceCity;
    @SerializedName("deliveryTime")
    private String deliveryTime;
    @SerializedName("deliveryWayName")
    private String deliveryWayName;
    @SerializedName("isChoiceDelivery")
    private String isChoiceDelivery;
    @SerializedName("weight")
    private String weight;
    @SerializedName("QTY")
    private String QTY;
    @SerializedName("price")
    private String price;
    @SerializedName("paymentModeName")
    private String paymentModeName;
    @SerializedName("inspectonBody")
    private String inspectonBody;
    @SerializedName("inspectonBody_Img")
    private String inspectonBodyImg;
    @SerializedName("povinceId")
    private String povinceId;
    @SerializedName("cityId")
    private String cityId;
    @SerializedName("state")
    private String state;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
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

    public String getIsChoiceDelivery() {
        return isChoiceDelivery;
    }

    public void setIsChoiceDelivery(String isChoiceDelivery) {
        this.isChoiceDelivery = isChoiceDelivery;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getPovinceId() {
        return povinceId;
    }

    public void setPovinceId(String povinceId) {
        this.povinceId = povinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
