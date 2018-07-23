package com.hengsheng.rxjava2demo.rxhttp.func;

import com.hengsheng.rxjava2demo.rxhttp.cache.model.CacheResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：缓存结果转换
 */

public class CacheResultFunc<T> implements Function<CacheResult<T>, T> {
    @Override
    public T apply(@NonNull CacheResult<T> tCacheResult) throws Exception {
        return tCacheResult.data;
    }
}
