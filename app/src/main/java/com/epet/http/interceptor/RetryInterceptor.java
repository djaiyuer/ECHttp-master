package com.epet.http.interceptor;

import android.util.Log;

import java.io.IOException;
import java.io.InterruptedIOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类名：RetryInterceptor
 * 描述：重连次数拦截器
 * 笔记：
 * 作者：hzw
 * 创建日期：2019/4/26
 * 修改：
 * 修改日期：
 */
public class RetryInterceptor implements Interceptor {

    private int retryCount;
    private long retryInterval;

    public RetryInterceptor(Builder builder) {
        this.retryCount = builder.retryCount;
        this.retryInterval = builder.retryInterval;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = doRequest(chain, request);
        int retryNum = 0;
        while ((response == null || !response.isSuccessful()) && retryNum <= retryCount) {
            Log.d("贺中伟", "retryNum = " + retryNum);
            final long nextInterval = getRetryInterval();
            try {
                Log.d("贺中伟", "nextInterval = " + nextInterval);
                Thread.sleep(nextInterval);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            }
            retryNum++;
            // retry the request
            response = doRequest(chain, request);
        }
        return response;
    }

    private Response doRequest(Chain chain, Request request) {
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
        }
        return response;
    }

    /**
     * retry间隔时间
     */
    public long getRetryInterval() {
        return this.retryInterval;
    }

    public static final class Builder {
        private int retryCount;
        private long retryInterval;

        public Builder() {
            retryCount = 3;
            retryInterval = 1000;
        }

        public RetryInterceptor.Builder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public RetryInterceptor.Builder retryInterval(long retryInterval) {
            this.retryInterval = retryInterval;
            return this;
        }

        public RetryInterceptor build() {
            return new RetryInterceptor(this);
        }
    }
}
