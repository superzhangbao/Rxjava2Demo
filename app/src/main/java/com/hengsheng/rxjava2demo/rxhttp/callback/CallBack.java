package com.hengsheng.rxjava2demo.rxhttp.callback;


import com.hengsheng.rxjava2demo.rxhttp.exception.ApiException;
import com.hengsheng.rxjava2demo.rxhttp.utils.Utils;

import java.lang.reflect.Type;

/**
 * Created by zhangb on 2018/7/18/018
 */

public abstract class CallBack<T> implements IType<T> {
    public abstract void onStart();

    public abstract void onCompleted();

    public abstract void onError(ApiException e);

    public abstract void onSuccess(T t);

    @Override
    public Type getType() {//获取需要解析的泛型T类型
        return Utils.findNeedClass(getClass());
    }

    public Type getRawType() {//获取需要解析的泛型T raw类型
        return Utils.findRawType(getClass());
    }
}
