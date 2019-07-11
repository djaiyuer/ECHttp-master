package com.epet.http.observer;

import com.epet.http.OnResultListener;
import com.epet.http.imple.HttpEngineImple;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 作者：yuer on 2017/8/31 15:59
 * 邮箱：heziyu222@163.com
 */

public class HttpGeneralObServer implements Observer<ResponseBody> {
    private HttpEngineImple.Builder mBuidler;
    private OnResultListener mListener;
    public HttpGeneralObServer(HttpEngineImple.Builder buidler){
        this.mBuidler = buidler;
        this.mListener = mBuidler.getListener();
    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResponseBody value) {
        if (mListener != null) {
            try {
                mListener.onSuccess(value);
                mListener.onSuccess(value.string().toString());
            }catch (IOException io){
                io.printStackTrace();
                mListener.onFailure(io);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mListener != null) {
            mListener.onNetworkError(e);
        }
    }

    @Override
    public void onComplete() {
        if (mListener != null) {
            mListener.onFinish();
        }
    }
}
