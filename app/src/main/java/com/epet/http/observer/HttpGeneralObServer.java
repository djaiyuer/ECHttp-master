package com.epet.http.observer;

import android.util.Log;

import com.epet.http.OnResultListener;
import com.epet.http.R;
import com.epet.http.imple.HttpEngineImple;
import com.epet.http.utils.Applications;
import com.epet.http.utils.NetworkUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * 作者：yuer on 2017/8/31 15:59
 * 邮箱：heziyu222@163.com
 */

public class HttpGeneralObServer implements Observer<ResponseBody> {
    public static final int NET_STATE_EXCEPTION_CODE_504 = 504;
    public static final int NET_STATE_EXCEPTION_CODE_404 = 404;

    private HttpEngineImple.Builder mBuidler;
    private OnResultListener mListener;
    public HttpGeneralObServer(HttpEngineImple.Builder buidler){
        this.mBuidler = buidler;
        this.mListener = mBuidler.getListener();
    }
    @Override
    public void onSubscribe(Disposable d) {
        if (this.mListener != null) {
            this.mListener.onRequestStart();
            if(!NetworkUtils.isNetWorkAvailable()){
                this.mListener.onNetworkError(false , Applications.context().getString(R.string.network_state_fail));
            }
        }
    }

    @Override
    public void onNext(ResponseBody value) {
        if (this.mListener != null) {
            try {
                if(!NetworkUtils.isNetWorkAvailable()){
                    this.mListener.onCache(value);
                }else{
                    this.mListener.onSuccess(value);
                    this.mListener.onSuccess(value.string().toString());
                }
            }catch (IOException io){
                io.printStackTrace();
                this.mListener.onFailure(io);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (this.mListener != null) {
            final boolean ISNETWORKAVAILABLE = true;
            try {
                //请求超时
                if (e instanceof SocketTimeoutException) {
                    this.mListener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_socket_timeout));
                //网络连接超时
                } else if (e instanceof ConnectException) {
                    this.mListener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_connect_timeout));
                //安全证书异常
                } else if (e instanceof SSLHandshakeException) {
                    this.mListener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_sslhandshake_exception));
                //请求的地址不存在
                } else if (e instanceof HttpException) {
                    int code = ((HttpException) e).code();
                    if (code == NET_STATE_EXCEPTION_CODE_504) {
                        this.mListener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_state_504_exception));
                    } else if (code == NET_STATE_EXCEPTION_CODE_404) {
                        this.mListener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_state_404_exception));
                    } else {
                        this.mListener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_request_fail));
                    }
                //域名解析失败
                } else if (e instanceof UnknownHostException) {
                    this.mListener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_unknownhost_exception));
                } else {
                    this.mListener.onNetworkError(ISNETWORKAVAILABLE , "error:" + e.getMessage());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                this.mListener.onNetworkError(ISNETWORKAVAILABLE , "error:" + e.getMessage());
            } finally {
                Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            }
        }



    }

    @Override
    public void onComplete() {
        if (this.mListener != null) {
            this.mListener.onRequestFinish();
        }
    }
}
