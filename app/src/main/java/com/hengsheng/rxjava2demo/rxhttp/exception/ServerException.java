package com.hengsheng.rxjava2demo.rxhttp.exception;

/**
 * Created by zhangb on 2018/7/18/018
 * 处理服务器异常
 */

public class ServerException extends RuntimeException {
    private int errCode;
    private String message;

    public ServerException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
