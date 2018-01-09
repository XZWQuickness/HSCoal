package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/9.
 */

public class SeekGoodsDetaile {


    /**
     * name : 品名（动力煤）
     * count : 3652
     * sendTime : 2017-12-30
     * fromAddress : 安徽宿州
     * userName : 徐先生
     * mobile : 15985224561
     * toAddress : 江苏徐州淮海西路120号颖都大厦
     * remark : 备注
     */

    @SerializedName("name")
    private String name;
    @SerializedName("count")
    private String count="0";
    @SerializedName("sendTime")
    private String sendTime;
    @SerializedName("fromAddress")
    private String fromAddress;
    @SerializedName("userName")
    private String userName;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("toAddress")
    private String toAddress;
    @SerializedName("remark")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
