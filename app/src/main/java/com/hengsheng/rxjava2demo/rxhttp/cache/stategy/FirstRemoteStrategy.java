package com.hengsheng.rxjava2demo.rxhttp.cache.stategy;

import com.hengsheng.rxjava2demo.rxhttp.cache.RxCache;
import com.hengsheng.rxjava2demo.rxhttp.cache.model.CacheResult;

import java.lang.reflect.Type;
import java.util.Arrays;

import io.reactivex.Observable;

/**
 * Created by zhangb on 2018/7/18/018
 * 先请求网络，网络请求失败，再加载缓存
 */

public final class FirstRemoteStrategy extends BaseStrategy {
    @Override
    public <T> Observable<CacheResult<T>> execute(RxCache rxCache, String key, long time, Observable<T> source, Type type) {
        Observable<CacheResult<T>> cache = loadCache(rxCache, type, key, time, true);
        Observable<CacheResult<T>> remote = loadRemote(rxCache, key, source, false);
        //return remote.switchIfEmpty(cache);
        return Observable
                .concatDelayError(Arrays.asList(remote, cache))
                .take(1);
    }
}
