package com.zqw.widely.bean;


import cn.bmob.v3.BmobUser;

/**
 * Created by S01 on 2017/4/27.
 */

public class Users extends BmobUser {
    private Boolean sex;
    private String photo;
    private Integer age;
    private String address;

    public Users() {
    }

    public Users(Boolean sex, String photo, Integer age, String address) {
        this.sex = sex;
        this.photo = photo;
        this.age = age;
        this.address = address;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
