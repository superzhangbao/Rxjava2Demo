package com.hengsheng.rxjava2demo.rxhttp.func;

import com.hengsheng.rxjava2demo.rxhttp.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：异常转换处理
 */

public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
        return Observable.error(ApiException.handleException(throwable));
    }
}