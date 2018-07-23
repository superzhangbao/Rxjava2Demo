package com.hengsheng.rxjava2demo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hengsheng.rxjava2demo.bean.MyData;
import com.hengsheng.rxjava2demo.rxhttp.EasyHttp;
import com.hengsheng.rxjava2demo.rxhttp.cache.converter.SerializableDiskConverter;
import com.hengsheng.rxjava2demo.rxhttp.cache.model.CacheMode;
import com.hengsheng.rxjava2demo.rxhttp.callback.CallBackProxy;
import com.hengsheng.rxjava2demo.rxhttp.callback.ProgressDialogCallBack;
import com.hengsheng.rxjava2demo.rxhttp.exception.ApiException;
import com.hengsheng.rxjava2demo.rxhttp.subscriber.IProgressDialog;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    public void onGet(View view) {
        mDisposable = EasyHttp.get("banner/json")
                .params("1","1")
                .cacheMode(CacheMode.FIRSTREMOTE)
                .cacheKey(this.getClass().getSimpleName())
                .cacheTime(-1)//缓存时间300s，默认-1永久缓存  okhttp和自定义缓存都起作用
                .cacheDiskConverter(new SerializableDiskConverter())//默认使用的是 new SerializableDiskConverter();
                .execute(new CallBackProxy<CustomApiResult<List<MyData>>, List<MyData>>(new ProgressDialogCallBack<List<MyData>>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(List<MyData> response) {
                        if (response != null)
                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                });

        //方式一
//         EasyHttp.get("http://japi.juhe.cn/joke/content/list.from")
//                .params("key", "f5236a9fb8fc75fac0a4d9b8c27a4e90")
//                .params("page", "1")
//                .params("pagesize", "10")
//                .params("sort", "asc")
//                .params("time", "1418745237")
//                .execute(new CallBackProxy<TestApiResult2<Result>, Result>(new ProgressDialogCallBack<Result>(mProgressDialog) {
//                    @Override
//                    public void onError(ApiException e) {
//                        super.onError(e);
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onSuccess(Result result) {
//                        if (result != null) Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EasyHttp.cancelSubscription(mDisposable);
    }
}
