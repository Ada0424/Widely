package com.zqw.widely.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.zqw.widely.util.HttpException;
import com.zqw.widely.util.LogUtils;

import java.util.List;

/**
 * Created by jayli on 2017/5/3 0003.
 * [JSON解析管理类]
 */

public class JsonMananger {
    static{
        TypeUtils.compatibleWithJavaBean = true;
    }

    public static <T> T jsonToBean(String json, Class<T> cls) throws HttpException {
        return JSON.parseObject(json, cls);
    }


    public static <T> List<T> jsonToList(String json, Class<T> cls) throws HttpException {
        return JSON.parseArray(json, cls);
    }


    public static String beanToJson(Object obj) throws HttpException{
        String result = JSON.toJSONString(obj);
        LogUtils.e("beanToJson: " + result);
        return result;
    }
}
