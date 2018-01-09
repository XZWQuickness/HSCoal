package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/9.
 */

public class SeekGoodsEntity {


    /**
     * id : 编号
     * fromAddress : 安徽宿州
     * toAddress : 江苏徐州
     * name : 品名（动力煤）
     * count : 500
     * sendTime : 2017-12-09
     */

    @SerializedName("id")
    private String id;
    @SerializedName("fromAddress")
    private String fromAddress;
    @SerializedName("toAddress")
    private String toAddress;
    @SerializedName("name")
    private String name;
    @SerializedName("count")
    private String count;
    @SerializedName("sendTime")
    private String sendTime;

    private String image;
    private String type;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

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
}
