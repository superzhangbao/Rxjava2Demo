package com.hengsheng.rxjava2demo.rxhttp.request;

import com.google.gson.reflect.TypeToken;
import com.hengsheng.rxjava2demo.rxhttp.cache.model.CacheResult;
import com.hengsheng.rxjava2demo.rxhttp.callback.CallBack;
import com.hengsheng.rxjava2demo.rxhttp.callback.CallBackProxy;
import com.hengsheng.rxjava2demo.rxhttp.func.ApiResultFunc;
import com.hengsheng.rxjava2demo.rxhttp.func.CacheResultFunc;
import com.hengsheng.rxjava2demo.rxhttp.func.RetryExceptionFunc;
import com.hengsheng.rxjava2demo.rxhttp.model.ApiResult;
import com.hengsheng.rxjava2demo.rxhttp.subscriber.CallBackSubsciber;
import com.hengsheng.rxjava2demo.rxhttp.utils.RxUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by zhangb on 2018/7/18/018
 * 描述：删除请求
 */

@SuppressWarnings(value = {"unchecked", "deprecation"})
public class DeleteRequest extends BaseBodyRequest<DeleteRequest> {
    public DeleteRequest(String url) {
        super(url);
    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Observable<CacheResult<T>> observable = build().toObservable(generateRequest(), proxy);
        if (CacheResult.class != proxy.getCallBack().getRawType()) {
            return observable.compose(new ObservableTransformer<CacheResult<T>, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
                    return upstream.map(new CacheResultFunc<T>());
                }
            }).subscribeWith(new CallBackSubsciber<T>(context, proxy.getCallBack()));
        } else {
            return observable.subscribeWith(new CallBackSubsciber<CacheResult<T>>(context, proxy.getCallBack()));
        }
    }

    private <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return observable.map(new ApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
        }.getType()))
                .compose(isSyncRequest ? RxUtil._main() : RxUtil._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallBack().getType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (this.requestBody != null) { //自定义的请求体
            return apiManager.deleteBody(url, this.requestBody);
        } else if (this.json != null) {//Json
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), this.json);
            return apiManager.deleteJson(url, body);
        }  else if (this.object != null) {//自定义的请求object
            return apiManager.deleteBody(url, object);
        } else if (this.string != null) {//文本内容
            RequestBody body = RequestBody.create(mediaType, this.string);
            return apiManager.deleteBody(url, body);
        } else {
            return apiManager.delete(url, params.urlParamsMap);
        }
    }
}
