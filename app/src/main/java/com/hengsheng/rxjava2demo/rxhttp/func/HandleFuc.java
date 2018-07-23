package com.hengsheng.rxjava2demo.rxhttp.func;

import com.hengsheng.rxjava2demo.rxhttp.exception.ApiException;
import com.hengsheng.rxjava2demo.rxhttp.exception.ServerException;
import com.hengsheng.rxjava2demo.rxhttp.model.ApiResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by zhangb on 2018/7/18/018
 * ApiResult<T>转换T
 */

public class HandleFuc<T> implements Function<ApiResult<T>, T> {
    @Override
    public T apply(@NonNull ApiResult<T> tApiResult) throws Exception {
        if (ApiException.isOk(tApiResult)) {
            return tApiResult.getData();// == null ? Optional.ofNullable(tApiResult.getData()).orElse(null) : tApiResult.getData();
        } else {
            throw new ServerException(tApiResult.getCode(), tApiResult.getMsg());
        }
    }
}
