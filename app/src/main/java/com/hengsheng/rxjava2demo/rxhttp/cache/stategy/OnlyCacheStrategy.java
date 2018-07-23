package com.hengsheng.rxjava2demo.rxhttp.cache.stategy;

import com.hengsheng.rxjava2demo.rxhttp.cache.RxCache;
import com.hengsheng.rxjava2demo.rxhttp.cache.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：只读缓存
 */

public final class OnlyCacheStrategy extends BaseStrategy{
    @Override
    public <T> Observable<CacheResult<T>> execute(RxCache rxCache, String key, long time, Observable<T> source, Type type) {
        return loadCache(rxCache,type,key,time,false);
    }
}
