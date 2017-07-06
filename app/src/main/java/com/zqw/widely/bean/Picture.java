package com.zqw.widely.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */

public class Picture implements Parcelable {
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;


    public Picture() {
    }

    public Picture(String ctime, String title, String description, String picUrl, String url) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    protected Picture(Parcel in) {
        ctime = in.readString();
        title = in.readString();
        description = in.readString();
        picUrl = in.readString();
        url = in.readString();
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ctime);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(picUrl);
        parcel.writeString(url);
    }
}
