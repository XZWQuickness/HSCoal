package com.exz.hscal.hscoal.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by pc on 2018/1/10.
 */

class InquiryOrderBean {

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
     * povinceCity : 江苏徐州
     * plannedDeliveryTime : 收货时间
     * createDate : 2017-12-08 16:46
     * price : 5000
     * state : 0
     */

    @SerializedName("id")
    var id=""
    @SerializedName("name")
    var name=""
    @SerializedName("coalVarietyName")
    var coalVarietyName=""
    @SerializedName("lowerCalorificValue")
    var lowerCalorificValue=""
    @SerializedName("ashSpecification")
    var ashSpecification=""
    @SerializedName("totalSulfurContent")
    var totalSulfurContent=""
    @SerializedName("fixedCarbon")
    var fixedCarbon=""
    @SerializedName("calorificValue")
    var calorificValue=""
    @SerializedName("purchaseQuantity")
    var purchaseQuantity=""
    var provinceCity=""
    @SerializedName("plannedDeliveryTime")
    var plannedDeliveryTime=""
    @SerializedName("createDate")
    var createDate=""
    @SerializedName("price")
    var price=""
    @SerializedName("state")
    var state=""
    var quoteCount=""

    /**
     * description : 分类/材质/规格
     */

    @SerializedName("description")
    var description=""
}
