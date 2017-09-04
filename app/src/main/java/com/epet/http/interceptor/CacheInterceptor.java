package com.epet.http.interceptor;

import android.content.Context;

import com.epet.http.config.HttpFrameConfig;
import com.epet.http.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http缓存拦截器
 * 作者：yuer on 2017/8/18 18:28
 * 邮箱：heziyu222@163.com
 */

public class CacheInterceptor implements Interceptor {
    //句柄
    private Context mContext;
    //缓存目录
    private File mCacheFile = null;
    //缓存大小
    public Cache mCache = null;
    public CacheInterceptor(Context context){
        this.mContext = context;
        mCacheFile = new File(mContext.getCacheDir(),"cache");
        mCache = new Cache(mCacheFile, HttpFrameConfig.HTTP_DISK_CACHE_SIZE);
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //网络不可用则使用缓存
        if(!NetworkUtils.isNetWorkAvailable(mContext)){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        return chain.proceed(request);
    }
}
