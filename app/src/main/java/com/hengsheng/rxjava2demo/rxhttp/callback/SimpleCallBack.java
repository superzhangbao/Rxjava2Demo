package com.hengsheng.rxjava2demo.rxhttp.callback;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：简单的回调,默认可以使用该回调，不用关注其他回调方法
 * 使用该回调默认只需要处理onError，onSuccess两个方法既成功失败
 */

public abstract class SimpleCallBack<T> extends CallBack<T> {

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {

    }
}