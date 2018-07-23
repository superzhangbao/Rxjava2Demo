package com.hengsheng.rxjava2demo.rxhttp.subscriber;

import android.content.Context;

import com.hengsheng.rxjava2demo.rxhttp.callback.CallBack;
import com.hengsheng.rxjava2demo.rxhttp.callback.ProgressDialogCallBack;
import com.hengsheng.rxjava2demo.rxhttp.exception.ApiException;

import io.reactivex.annotations.NonNull;

/**
 * Created by zhangb on 2018/7/18/018
 * 带有callBack的回调
 * 主要作用是不需要用户订阅，只要实现callback回调
 */

public class CallBackSubsciber<T> extends BaseSubscriber<T> {
    public CallBack<T> mCallBack;


    public CallBackSubsciber(Context context, CallBack<T> callBack) {
        super(context);
        mCallBack = callBack;
        if (callBack instanceof ProgressDialogCallBack) {
            ((ProgressDialogCallBack) callBack).subscription(this);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mCallBack != null) {
            mCallBack.onStart();
        }
    }

    @Override
    public void onError(ApiException e) {
        if (mCallBack != null) {
            mCallBack.onError(e);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        super.onNext(t);
        if (mCallBack != null) {
            mCallBack.onSuccess(t);
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (mCallBack != null) {
            mCallBack.onCompleted();
        }
    }
}
