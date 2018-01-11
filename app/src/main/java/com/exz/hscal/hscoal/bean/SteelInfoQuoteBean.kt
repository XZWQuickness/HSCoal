package com.exz.hscal.hscoal.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by pc on 2018/1/11.
 */

class SteelInfoQuoteBean {


    /**
     * state : 0
     * price : 5000
     * createDate : 2017-12-08 16:46
     * auditStateTime : 确认/拒绝时间
     * name : 品名
     * className : 分类名称
     * purchaseQuantity : 求购数量（件）
     * specification : 规格
     * materialQuality : 材质
     * weight : 件重
     * provinceCity : 江苏徐州
     * placeDelivery : 淮海西路120号
     * plannedDeliveryTime : 2017-12-30
     * contactName : 徐先生
     * contactMobile : 15895226665
     * remark : 备注
     */

    @SerializedName("state")
    var state = ""
    @SerializedName("price")
    var price = ""
    @SerializedName("createDate")
    var createDate = ""
    @SerializedName("auditStateTime")
    var auditStateTime = ""
    @SerializedName("name")
    var name = ""
    @SerializedName("className")
    var className = ""
    @SerializedName("purchaseQuantity")
    var purchaseQuantity = ""
    @SerializedName("specification")
    var specification = ""
    @SerializedName("materialQuality")
    var materialQuality = ""
    @SerializedName("weight")
    var weight = ""
    @SerializedName("provinceCity")
    var provinceCity = ""
    @SerializedName("placeDelivery")
    var placeDelivery = ""
    @SerializedName("plannedDeliveryTime")
    var plannedDeliveryTime = ""
    @SerializedName("contactName")
    var contactName = ""
    @SerializedName("contactMobile")
    var contactMobile = ""
    @SerializedName("remark")
    var remark = ""
}
