package com.hengsheng.rxjava2demo.rxhttp.cache.core;

import java.lang.reflect.Type;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：内存缓存
 * 内存缓存针对缓存的时间不好处理，暂时没有写内存缓存，等后面有思路了，再加上该部分
 */

public class MemoryCache extends BaseCache {
    @Override
    protected boolean doContainsKey(String key) {
        return false;
    }

    @Override
    protected boolean isExpiry(String key, long existTime) {
        return false;
    }

    @Override
    protected <T> T doLoad(Type type, String key) {
        return null;
    }

    @Override
    protected <T> boolean doSave(String key, T value) {
        return false;
    }

    @Override
    protected boolean doRemove(String key) {
        return false;
    }

    @Override
    protected boolean doClear() {
        return false;
    }
}
