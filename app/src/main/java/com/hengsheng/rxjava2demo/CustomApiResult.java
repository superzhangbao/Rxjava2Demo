package com.hengsheng.rxjava2demo;

import com.hengsheng.rxjava2demo.rxhttp.model.ApiResult;

/**
 * Created by zhangb on 2018/7/18/18
 * 自定义ApiResult
 * ApiResult定义的是标准的{code，msg，data}json格式
 * 若自身与标准不一致的情况下可自定义，但是要注意，与标准字段一样的不用重写，不一样的需要重写getXXX()
 * 重写isOk()，自定义成功code
 */

public class CustomApiResult<T> extends ApiResult<T> {
    String errorMsg;
    int errorCode;

    @Override
    public String getMsg() {
        return errorMsg;
    }

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public boolean isOk() {
        return errorCode == 0;
    }

    @Override
    public String toString() {
        return "CustomApiResult{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
