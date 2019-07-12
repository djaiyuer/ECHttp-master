package com.epet.http.observer;


import com.epet.http.listener.OnResultListener;
import com.epet.http.imple.HttpEngineImple;
import com.epet.http.utils.NetworkUtils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 作者：yuer on 2017/8/31 15:59
 * 邮箱：heziyu222@163.com
 */

public class HttpGeneralObServer extends HttpObServer implements Observer<ResponseBody> {

    public HttpGeneralObServer(HttpEngineImple.Builder buidler){
        super(buidler);
    }
    @Override
    public void onSubscribe(Disposable d) {
        super.startRequest();
    }

    @Override
    public void onNext(ResponseBody value) {
        OnResultListener listener =  this.mObserverOnNextListener.get();
        if (listener != null) {
            try {
                if(!NetworkUtils.isNetWorkAvailable()){
                    listener.onCache(value);
                    listener.onCache(value.string().toString());
                }else{
                    listener.onSuccess(value);
                    listener.onSuccess(value.string().toString());
                }
            }catch (IOException io){
                io.printStackTrace();
                listener.onFailure(io);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        super.httpExcpetion(e);
    }

    @Override
    public void onComplete() {
        OnResultListener listener =  this.mObserverOnNextListener.get();
        if (listener != null) {
            listener.onRequestFinish();
        }
    }
}
