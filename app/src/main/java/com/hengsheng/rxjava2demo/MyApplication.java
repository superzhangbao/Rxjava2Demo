package com.hengsheng.rxjava2demo;

import android.app.Application;
import android.content.Context;

import com.hengsheng.rxjava2demo.rxhttp.EasyHttp;
import com.hengsheng.rxjava2demo.rxhttp.cache.converter.SerializableDiskConverter;
import com.hengsheng.rxjava2demo.rxhttp.utils.HttpLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


/**
 * Created by zhangb on 2018/7/18/018
 */

public class MyApplication extends Application {

    private static Application app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        EasyHttp.init(this);//默认初始化,必须调用


        String Url = "http://www.wanandroid.com/";
        //设置请求头
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
//        //设置请求参数
//        HttpParams params = new HttpParams();
//        params.put("appId", AppConstant.APPID);
        EasyHttp.getInstance()
                .debug("RxEasyHttp", true)
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setRetryCount(2)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(Url)
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setCacheVersion(1)//缓存版本为1
                .setHostnameVerifier(new UnSafeHostnameVerifier(Url))//全局访问规则
                .setCertificates();//信任所有证书
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
//                .addCommonHeaders(headers)//设置全局公共头
//                .addCommonParams(params)//设置全局公共参数
//                .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
//.addInterceptor(new HeTInterceptor());//处理自己业务的拦截器
    }

    public class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            HttpLog.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            HttpLog.i("############### verify " + hostname + " " + this.host);
            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
                return false;
            return true;
        }
    }

    /**
     * 获取Application的Context
     **/
    public static Context getAppContext() {
        if (app == null)
            return null;
        return app.getApplicationContext();
    }
}
