package com.hengsheng.rxjava2demo.rxhttp.cache.stategy;

import com.hengsheng.rxjava2demo.rxhttp.cache.RxCache;
import com.hengsheng.rxjava2demo.rxhttp.cache.model.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by zhangb on 2018/7/18/018
 * 网络加载，不缓存
 */

public class NoStrategy implements IStrategy {
    @Override
    public <T> Observable<CacheResult<T>> execute(RxCache rxCache, String cacheKey, long cacheTime, Observable<T> source, Type type) {
        return source.map(new Function<T, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(@NonNull T t) throws Exception {
                return new CacheResult<T>(false, t);
            }
        });
    }
}
