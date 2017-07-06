package com.zqw.widely;

import android.app.Application;


import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/5.
 */

public class BaseApplication extends Application {
    private static final String TAG = "WIDELY";
    private static BaseApplication instance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = (BaseApplication) getApplicationContext();
        ShareSDK.initSDK(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .build();

      OkHttpUtils.initClient(okHttpClient);
    }
    public static synchronized BaseApplication getInstance(){
        return instance;
    }

}
