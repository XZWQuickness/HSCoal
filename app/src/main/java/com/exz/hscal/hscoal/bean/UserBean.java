package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017/12/28.
 */

public class UserBean {

    /**
     * mobile : 13854802145
     * nickname : 老堂堂堂堂主
     * headImg : http://123.png
     * tel : 0516-85645800
     * qq : 3067727898
     */

    @SerializedName("mobile")
    private String mobile;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("headImg")
    private String headImg;
    @SerializedName("tel")
    private String tel;
    @SerializedName("qq")
    private String qq;
    private String businessAuthentication;
    private String driverAuthentication;

    public String getBusinessAuthentication() {
        return businessAuthentication;
    }

    public void setBusinessAuthentication(String businessAuthentication) {
        this.businessAuthentication = businessAuthentication;
    }

    public String getDriverAuthentication() {
        return driverAuthentication;
    }

    public void setDriverAuthentication(String driverAuthentication) {
        this.driverAuthentication = driverAuthentication;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
