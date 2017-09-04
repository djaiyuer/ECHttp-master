package com.epet.http.observer;

import com.epet.http.OnResultListener;
import com.epet.http.entity.DownInfoEntity;
import com.epet.http.imple.HttpEngineImple;
import com.epet.http.listener.DownLoadingProgressListener;

import java.io.IOException;
import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 作者：yuer on 2017/8/31 15:59
 * 邮箱：heziyu222@163.com
 */

public class HttpDownLoadObServer implements Observer<DownInfoEntity>{
    private HttpEngineImple.Builder mBuidler;
    /*下载数据*/
    private DownInfoEntity mDownInfo;
    //弱引用结果回调
    private SoftReference<OnResultListener> mObserverOnNextListener;
    public HttpDownLoadObServer(HttpEngineImple.Builder buidler){
        this.mBuidler = buidler;
        this.mObserverOnNextListener = new SoftReference<>(mBuidler.getListener());
        this.mDownInfo = buidler.getDownLoadInfo();
    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(DownInfoEntity value) {
        try {
            if (mObserverOnNextListener.get() != null) {
                mObserverOnNextListener.get().onSuccess(value);
            }
        }catch (Exception io){
            io.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mObserverOnNextListener.get() != null) {
            mObserverOnNextListener.get().onFailure(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

}
