package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017/12/28.
 */

public class NewsBean {


    /**
     * title : 产地动力煤：山西煤矿停产数量比较多
     * url : http://123.aspx
     */

    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
