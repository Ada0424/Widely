package com.zqw.widely.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 2017/4/15.
 */

public class UserInfo implements Parcelable {
    private String userID;
    private String icon;
    private String token;
    private String nickname;

    public UserInfo(String userID, String icon, String token, String nickname) {
        this.userID = userID;
        this.icon = icon;
        this.token = token;
        this.nickname = nickname;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.icon);
        dest.writeString(this.token);
        dest.writeString(this.nickname);
    }

    protected UserInfo(Parcel in) {
        this.userID = in.readString();
        this.icon = in.readString();
        this.token = in.readString();
        this.nickname = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
