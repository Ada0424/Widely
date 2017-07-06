package com.zqw.widely.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by S01 on 2017/5/20.
 */

public class Collection extends BmobObject {
    private String uId = "";
    private Integer type = 0;
    private String title = "";
    private String picUrl = "";
    private String url = "";

    public Collection() {
    }

    public Collection(String uId, Integer type, String title, String picUrl, String url) {
        this.uId = uId;
        this.type = type;
        this.title = title;
        this.picUrl = picUrl;
        this.url = url;
    }

    public Collection(String tableName) {
        super(tableName);
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
