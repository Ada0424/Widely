package com.zqw.widely.common;

/**
 * Created by Administrator on 2017/5/5.
 */

public class Common {
    public static final String API_NEWS_KEY = "727256b2747de604820c84c573092866";
    public static final String  JOHE_KEY= "36c134411faf2876f29d2a665e78b27b";
    public static final String  PIC_KEY= "3fac1df8a629f331d68f1e7aba87a4fd";


    /** 是否第一次运行 **/
    public static final String IS_FIRST_RUN = "isFirstRun";
    /** 是否登录 */
    public static final String IS_LOGIN = "isLogin";
    /** 用户头像地址 **/
    public static final String USER_PHOTO = "user_photo";
    /** 用户头昵称 **/
    public static final String USER_NAME = "user_name";
    /** 用户头密码 **/
    public static final String USER_PWD = "user_pwd";
    /** 用户登录方式 **/
    public static final String LOGINTYPE = "login_type";

    public static final int LOGIN_TYPE_NORMAL = 0X001;
    public static final int LOGIN_TYPE_THIRD = 0X002;

    public static final int COLLECTION_TYPE_NEWS = 0X001;
    public static final int COLLECTION_TYPE_JOKE = 0X002;
    public static final int COLLECTION_TYPE_PIC = 0X003;
}
