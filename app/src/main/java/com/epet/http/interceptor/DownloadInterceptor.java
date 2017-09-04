package com.epet.http.interceptor;

import com.epet.http.listener.DownLoadingProgressListener;
import com.epet.http.body.DownloadResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 下载拦截器
 * 作者：yuer on 2017/8/28 11:40
 * 邮箱：heziyu222@163.com
 */

public class DownloadInterceptor implements Interceptor {
    private DownLoadingProgressListener mProgressListener;
    public DownloadInterceptor(DownLoadingProgressListener progressListener){
        this.mProgressListener = progressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), mProgressListener))
                .build();
    }
}
