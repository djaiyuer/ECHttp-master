package com.epet.http.utils.okhttps;

import android.text.TextUtils;

import com.epet.http.OnResultListener;
import com.epet.http.config.HttpFrameConfig;
import com.epet.http.cookie.InDiskCookieStore;
import com.epet.http.entity.DownInfoEntity;
import com.epet.http.https.SslSocketFactory;
import com.epet.http.imple.HttpEngineImple;
import com.epet.http.interceptor.BaseInterceptor;
import com.epet.http.interceptor.CacheInterceptor;
import com.epet.http.interceptor.DownloadInterceptor;
import com.epet.http.interceptor.LoggingInterceptor;
import com.epet.http.interceptor.RetryInterceptor;
import com.epet.http.listener.DownLoadingProgressListener;
import com.epet.http.interceptor.NetworkInterceptor;
import com.epet.http.utils.Applications;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

/**
 * okhttp帮助类
 * 作者：yuer on 2017/8/21 19:16
 * 邮箱：heziyu222@163.com
 */

public class OkHttpClientUtils {
    private OkHttpClient mOkHttpClient;
    private OkHttpClient.Builder mOkHttpClientBuilder;
    /**
     * 单例模式
     * @return OkHttpClientUtils
     */
    public static OkHttpClientUtils newInstance() {
        return InstanceHolder.okHttpClientUtils;
    }
    /**
     * 创建okhttpclient的builder
     * @return
     */
    public OkHttpClient createOkHttpCilentBuilder(HttpEngineImple.Builder buidler){
        if(this.mOkHttpClient == null){
            //缓存拦截器
            CacheInterceptor cacheInterceptor  = new CacheInterceptor();
            //自定义OkHttpClient
            this.mOkHttpClientBuilder = new OkHttpClient.Builder()
                    //失败重连
                    .retryOnConnectionFailure(true)
                    //超时时间
                    .connectTimeout(HttpFrameConfig.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    //读取时间
                    .readTimeout(HttpFrameConfig.TIMEOUT_READ, TimeUnit.SECONDS)
                    //写入时间
                    .writeTimeout(HttpFrameConfig.TIMEOUT_WRITE,TimeUnit.SECONDS)
                    //缓存拦截器
                    .addInterceptor(cacheInterceptor)
                    .cache(cacheInterceptor.mCache)
                    //网络拦截器
                    .addInterceptor(new NetworkInterceptor())
                    .addNetworkInterceptor(new NetworkInterceptor())
                    //chrome工具调试的中间件
                    .addNetworkInterceptor(new StethoInterceptor());
            this.mOkHttpClientBuilder.addInterceptor(new RetryInterceptor.Builder().retryCount(3).retryInterval(5000).build());
            //日志拦截
            addLoggingInterceptor(this.mOkHttpClientBuilder);
            //设置证书
            setSslSocketFactory(this.mOkHttpClientBuilder, buidler);
            this.mOkHttpClient = this.mOkHttpClientBuilder.build();
        }
        boolean isDownload = false;
        //添加下载进度拦截
        DownInfoEntity downInfoEntity = buidler.getDownLoadInfo();
        if(downInfoEntity != null && !TextUtils.isEmpty(downInfoEntity.getSaveFileName())){
            isDownload = true;
            addDownLoadInterceptor(this.mOkHttpClientBuilder,buidler);
        }

        boolean isAddInterceptor = false;
        List<BaseInterceptor> interceptors = buidler.getInterceptors();
        if(interceptors != null && !interceptors.isEmpty()){
            isAddInterceptor = true;
            for (BaseInterceptor interceptor : interceptors) {
                mOkHttpClientBuilder.addInterceptor(interceptor);
            }
        }
        if(isDownload || isAddInterceptor){
            this.mOkHttpClient = this.mOkHttpClientBuilder.build();
        }
        return this.mOkHttpClient;
    }

    /**
     * 设置cookie
     */
    private  JavaNetCookieJar setCookies() {
        CookieManager cookieManager = new java.net.CookieManager(new InDiskCookieStore(Applications.context()), CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar javaNetCookieJar =  new JavaNetCookieJar(cookieManager);
        // builder.cookieJar(javaNetCookieJar);
        return javaNetCookieJar;
    }

    /**
     * 设置https证书
     * @param builder
     */
    private  void setSslSocketFactory(OkHttpClient.Builder builder,HttpEngineImple.Builder mBuidler){
        try {
            HashMap<String,String> certificates = HttpFrameConfig.httpsCertificateMaps;
            String baseUrl = mBuidler.getBaseUrl();
            if(certificates.containsKey(baseUrl)){
                String certificatePath = certificates.get(baseUrl);
                if(TextUtils.isEmpty(certificatePath)){
                    return;
                }
                InputStream inputStream = Applications.context().getAssets().open(certificatePath);
                if(inputStream==null){
                    return;
                }
                builder.sslSocketFactory(SslSocketFactory.getSSLSocketFactory(inputStream));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加日志拦截器
     */
    private void addLoggingInterceptor(OkHttpClient.Builder okHttpClient){
        okHttpClient.addNetworkInterceptor(new LoggingInterceptor());
    }

    /**
     * 下载拦截器
     * @param okHttpClient
     * @param buidler
     */
    private void addDownLoadInterceptor(final OkHttpClient.Builder okHttpClient,final HttpEngineImple.Builder buidler){
        final DownloadInterceptor downloadInterceptor =  new DownloadInterceptor(new DownLoadingProgressListener() {
            @Override
            public void onProgress(long writtenBytesCount, long totalBytesCount, boolean isFinish) {
                DownInfoEntity downInfo = buidler.getDownLoadInfo();
                if(downInfo.getTotalLength() > writtenBytesCount){
                    writtenBytesCount = downInfo.getTotalLength() - totalBytesCount + writtenBytesCount;
                }else{
                    downInfo.setTotalLength(totalBytesCount);
                }
                downInfo.setReadLength(writtenBytesCount);
                OnResultListener listener = buidler.getListener();
                if(listener == null){
                    return;
                }
                if(isFinish){
                    listener.downLoadComplate(buidler.getDownLoadInfo().getSavePath() + buidler.getDownLoadInfo().getSaveFileName());
                }else{
                    listener.downLoadProgress(writtenBytesCount,totalBytesCount);
                }
            }
        });
        okHttpClient.addNetworkInterceptor(downloadInterceptor);
    }

    private static class InstanceHolder{
        private static final OkHttpClientUtils okHttpClientUtils = new OkHttpClientUtils();
    }
}
