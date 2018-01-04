package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;
import com.szw.framelibrary.entities.AbsUser;

/**
 * Created by pc on 2017/12/28.
 */

public class LoginEntity extends AbsUser{

    /**
     * userId : 1
     */

    @SerializedName("userId")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
