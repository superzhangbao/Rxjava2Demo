package com.hengsheng.rxjava2demo.rxhttp.cache.core;

import com.hengsheng.rxjava2demo.rxhttp.utils.HttpLog;
import com.hengsheng.rxjava2demo.rxhttp.utils.Utils;

import java.lang.reflect.Type;

import okio.ByteString;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：缓存核心管理类
 *
 * 1.采用LruDiskCache
 * 2.对Key进行MD5加密
 *
 * 以后可以扩展 增加内存缓存，但是内存缓存的时间不好控制，暂未实现，后续可以添加
 */

public class CacheCore {

    private LruDiskCache disk;

    public CacheCore(LruDiskCache disk) {
        this.disk = Utils.checkNotNull(disk, "disk==null");
    }


    /**
     * 读取
     */
    public synchronized <T> T load(Type type, String key, long time) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        HttpLog.d("loadCache  key=" + cacheKey);
        if (disk != null) {
            T result = disk.load(type, cacheKey, time);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * 保存
     */
    public synchronized <T> boolean save(String key, T value) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        HttpLog.d("saveCache  key=" + cacheKey);
        return disk.save(cacheKey, value);
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public synchronized boolean containsKey(String key) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        HttpLog.d("containsCache  key=" + cacheKey);
        if (disk != null) {
            if (disk.containsKey(cacheKey)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public synchronized boolean remove(String key) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        HttpLog.d("removeCache  key=" + cacheKey);
        if (disk != null) {
            return disk.remove(cacheKey);
        }
        return true;
    }

    /**
     * 清空缓存
     */
    public synchronized boolean clear() {
        if (disk != null) {
            return disk.clear();
        }
        return false;
    }

}
