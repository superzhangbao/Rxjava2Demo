package com.hengsheng.rxjava2demo.easyhttpuse;

import android.text.TextUtils;

import com.hengsheng.rxjava2demo.MyApplication;

/**
 * Created by zhangb on 2018/7/18/018
 */

public class TokenManager {
    private final static String key = "auth_model_new";
    private static TokenManager instance = null;
    private AuthModel authModel;
    private ACache aCache;
    private Long timestamp = System.currentTimeMillis();

    public TokenManager() {
        aCache = ACache.get(MyApplication.getAppContext(), key);
        this.authModel = new AuthModel();
        this.authModel.setAccessToken("");
    }

    public static TokenManager getInstance() {
        if (instance == null) {
            synchronized (TokenManager.class) {
                if (instance == null) {
                    instance = new TokenManager();
                }
            }
        }
        return instance;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public AuthModel getAuthModel() {
        if (this.authModel == null || this.authModel.getAccessToken() == null || this.authModel.getAccessToken().equals("")) {
            Object object = aCache.getAsObject(ComParamContact.Token.AUTH_MODEL);
            if (object != null) {
                this.authModel = (AuthModel) object;
            }
        }
        return this.authModel;
    }

    public void setAuthModel(AuthModel model) {
        if (model != null) {
            this.authModel = model;
            aCache.put(ComParamContact.Token.AUTH_MODEL, this.authModel);
        }
    }

    public void clearAuth() {
        AuthModel auth = new AuthModel();
        auth.setAccessToken("");
        this.authModel = auth;
        aCache.put(ComParamContact.Token.AUTH_MODEL, this.authModel);
        aCache.clear();
    }

    public boolean isLogin() {
        if (getAuthModel() != null && !TextUtils.isEmpty(getAuthModel().getAccessToken())) {
            return true;
        }
        return false;
    }
}
