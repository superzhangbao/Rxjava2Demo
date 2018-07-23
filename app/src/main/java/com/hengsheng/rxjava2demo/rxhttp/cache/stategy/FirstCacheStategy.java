package com.hengsheng.rxjava2demo.rxhttp.cache.stategy;

import com.hengsheng.rxjava2demo.rxhttp.cache.RxCache;
import com.hengsheng.rxjava2demo.rxhttp.cache.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：先显示缓存，缓存不存在，再请求网络
 */

final public class FirstCacheStategy extends BaseStrategy {
    @Override
    public <T> Observable<CacheResult<T>> execute(RxCache rxCache, String key, long time, Observable<T> source, Type type) {
        Observable<CacheResult<T>> cache = loadCache(rxCache, type, key, time, true);
        Observable<CacheResult<T>> remote = loadRemote(rxCache, key, source, false);
        return cache.switchIfEmpty(remote);
    }
}
