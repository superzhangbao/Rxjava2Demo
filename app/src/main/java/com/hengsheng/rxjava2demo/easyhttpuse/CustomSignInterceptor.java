package com.hengsheng.rxjava2demo.easyhttpuse;

import com.hengsheng.rxjava2demo.rxhttp.interceptor.BaseDynamicInterceptor;
import com.hengsheng.rxjava2demo.rxhttp.utils.HttpLog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

import static com.hengsheng.rxjava2demo.rxhttp.utils.HttpUtil.UTF8;

/**
 * Created by zhangb on 2018/7/18/018
 */

class CustomSignInterceptor  extends BaseDynamicInterceptor<CustomSignInterceptor> {
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            dynamicMap.put(
                    ComParamContact.Common.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        }
        if (isAccessToken()) {//是否添加token
            String acccess = TokenManager.getInstance().getAuthModel().getAccessToken();
            dynamicMap.put(ComParamContact.Common.ACCESSTOKEN, acccess);
        }
        if (isSign()) {//是否签名,因为你的字段key可能不是sign，这种动态的自己处理
            dynamicMap.put(ComParamContact.Common.SIGN, sign(dynamicMap));
        }
        //HttpLog.i("dynamicMap:" + dynamicMap.toString());
        return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }

    //签名规则：POST+url+参数的拼装+secret
    private String sign(TreeMap<String, String> dynamicMap) {
        String url = getHttpUrl().url().toString();
        //url = url.replaceAll("%2F", "/");
        StringBuilder sb = new StringBuilder("POST");
        sb.append(url);
        for (Map.Entry<String, String> entry : dynamicMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append(AppConstant.APP_SECRET);
        String signStr = sb.toString();
        try {
            signStr = URLDecoder.decode(signStr, UTF8.name());
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        HttpLog.i(signStr);
        return MD5.encode(signStr);
    }
}
