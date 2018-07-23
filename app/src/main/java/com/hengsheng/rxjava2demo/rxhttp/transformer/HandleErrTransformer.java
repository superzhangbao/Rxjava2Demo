package com.hengsheng.rxjava2demo.rxhttp.transformer;

import com.hengsheng.rxjava2demo.rxhttp.func.HttpResponseFunc;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;

/**
 * Created by zhangb on 2018/7/18/018
 * 错误转换Transformer
 */

public class HandleErrTransformer<T> implements ObservableTransformer<T, T> {
    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return upstream.onErrorResumeNext(new HttpResponseFunc<T>());
    }
}
