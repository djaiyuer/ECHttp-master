package com.epet.http.observer;

import android.util.Log;

import com.epet.http.OnResultListener;
import com.epet.http.R;
import com.epet.http.imple.HttpEngineImple;
import com.epet.http.utils.Applications;
import com.epet.http.utils.NetworkUtils;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

public class HttpObServer {
    protected static final int NET_STATE_EXCEPTION_CODE_504 = 504;
    protected static final int NET_STATE_EXCEPTION_CODE_404 = 404;
    protected HttpEngineImple.Builder mBuidler;
    //弱引用结果回调
    protected SoftReference<OnResultListener> mObserverOnNextListener;
    public HttpObServer(HttpEngineImple.Builder buidler){
        this.mBuidler = buidler;
        this.mObserverOnNextListener = new SoftReference<>(mBuidler.getListener());
    }

    /**
     * 请开始之前
     */
    protected void startRequest(){
        OnResultListener listener =  this.mObserverOnNextListener.get();
        if (listener != null) {
            listener.onRequestStart();
            if(!NetworkUtils.isNetWorkAvailable()){
                listener.onNetworkError(false , Applications.context().getString(R.string.network_state_fail));
            }
        }
    }

    /**
     * 网络异常情况处理方法
     * @param e 异常对象
     */
    protected void httpExcpetion(Throwable e){
        OnResultListener listener =  this.mObserverOnNextListener.get();
        if (listener != null) {
            final boolean ISNETWORKAVAILABLE = true;
            try {
                //请求超时
                if (e instanceof SocketTimeoutException) {
                    listener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_socket_timeout));
                    //网络连接超时
                } else if (e instanceof ConnectException) {
                    listener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_connect_timeout));
                    //安全证书异常
                } else if (e instanceof SSLHandshakeException) {
                    listener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_sslhandshake_exception));
                    //请求的地址不存在
                } else if (e instanceof HttpException) {
                    int code = ((HttpException) e).code();
                    if (code == NET_STATE_EXCEPTION_CODE_504) {
                        listener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_state_504_exception));
                    } else if (code == NET_STATE_EXCEPTION_CODE_404) {
                        listener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_state_404_exception));
                    } else {
                        listener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_request_fail));
                    }
                    //域名解析失败
                } else if (e instanceof UnknownHostException) {
                    listener.onNetworkError(ISNETWORKAVAILABLE , Applications.context().getString(R.string.network_unknownhost_exception));
                } else {
                    listener.onNetworkError(ISNETWORKAVAILABLE , "error:" + e.getMessage());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                listener.onNetworkError(ISNETWORKAVAILABLE , "error:" + e.getMessage());
            } finally {
                Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            }
        }
    }
}
