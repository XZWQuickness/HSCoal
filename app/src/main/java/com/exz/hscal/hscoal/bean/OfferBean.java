package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2018/1/11.
 */

public class OfferBean {


    /**
     * id : 编号
     * companyName : 公司名称
     * price : 5000
     * remark : 备注说明
     * state : 0待确认 1已接受 2已拒绝 3已过期
     */

    @SerializedName("id")
    private String id;
    @SerializedName("companyName")
    private String companyName;
    @SerializedName("price")
    private String price;
    @SerializedName("remark")
    private String remark;
    @SerializedName("state")
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
