package com.epet.http.observer;

import com.epet.http.listener.OnResultListener;
import com.epet.http.bean.DownInfoBean;
import com.epet.http.imple.HttpEngineImple;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：yuer on 2017/8/31 15:59
 * 邮箱：heziyu222@163.com
 */

public class HttpDownLoadObServer extends HttpObServer implements Observer<DownInfoBean>{
    private DownInfoBean mDownInfo;
    public HttpDownLoadObServer(HttpEngineImple.Builder buidler){
        super(buidler);
    }
    @Override
    public void onSubscribe(Disposable d) {
        super.startRequest();
    }

    @Override
    public void onNext(DownInfoBean value) {
        OnResultListener listener =  this.mObserverOnNextListener.get();
        try {
            if (listener != null) {
                this.mDownInfo = value;
                listener.onSuccess(value);
            }
        }catch (Exception io){
            io.printStackTrace();
            listener.onFailure(io);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.httpExcpetion(e);
    }

    @Override
    public void onComplete() {
        OnResultListener listener =  this.mObserverOnNextListener.get();
        if(listener != null && this.mDownInfo != null){
            listener.downLoadComplate(this.mDownInfo.getSavePath() , mDownInfo.getSaveFileName());
        }
    }
}
