package com.hengsheng.rxjava2demo.bean;

import com.hengsheng.rxjava2demo.rxhttp.model.ApiResult;

/**
 * Created by zhangb on 2018/7/23/023
 */

public class TestApiResult1<T> extends ApiResult<T> {
    //{"resultcode":"200","reason":"Return Successd!","result":{"province":"广东","city":"深圳","areacode":"0755","zip":"18000","company":"联通","card":""},"error_code":0}
    String reason;
    int error_code;
    //int resultcode;
    T result;

    @Override
    public int getCode() {
        return error_code;
    }


    @Override
    public String getMsg() {
        return reason;
    }


    @Override
    public T getData() {
        return result;
    }

    @Override
    public boolean isOk() {
        return getCode()==0;//如果不是0表示成功，请重写isOk()方法。
    }

    @Override
    public String toString() {
        return "PhoneIpApiBean{" +
                "reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", result=" + result +
                '}';
    }

}
