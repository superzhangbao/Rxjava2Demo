package com.hengsheng.rxjava2demo.rxhttp.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hengsheng.rxjava2demo.rxhttp.utils.HttpLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：设置缓存功能
 */

public class CacheInterceptor implements Interceptor{

    protected Context context;
    protected String cacheControlValue_Offline;
    protected String cacheControlValue_Online;
    //设置缓存时间
    protected static final int maxStale = 60 * 60 * 24 * 3;
    //读取缓存最大时间
    protected static final int maxStaleOnline = 60;


    public CacheInterceptor(Context context) {
        this(context, String.format("max-age=%d", maxStaleOnline));
    }

    public CacheInterceptor(Context context, String cacheControlValue) {
        this(context, cacheControlValue, String.format("max-age=%d", maxStale));
    }

    public CacheInterceptor(Context context, String cacheControlValueOffline, String cacheControlValueOnline) {
        this.context = context;
        this.cacheControlValue_Offline = cacheControlValueOffline;
        this.cacheControlValue_Online = cacheControlValueOnline;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String cacheControl = response.header("Cache-Control");
        HttpLog.e(maxStaleOnline+"s load cache:" + cacheControl);
        if (TextUtils.isEmpty(cacheControl) || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age") || cacheControl.contains("max-stale")) {
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxStale)
                    .build();
        } else {
            return response;
        }
    }
}
