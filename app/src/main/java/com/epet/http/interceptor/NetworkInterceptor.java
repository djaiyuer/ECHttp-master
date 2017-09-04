package com.epet.http.interceptor;

import android.content.Context;

import com.epet.http.config.HttpFrameConfig;
import com.epet.http.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络拦截器
 * 作者：yuer on 2017/8/18 19:53
 * 邮箱：heziyu222@163.com
 */

public class NetworkInterceptor implements Interceptor {
    //句柄
    private Context mContext;
    public NetworkInterceptor(Context context){
        this.mContext = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //网络可用
        if(NetworkUtils.isNetWorkAvailable(mContext)){
            int maxAge = 0 * 60;
            response = response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .build();
        }else{
            // 无网络时，设置超时为1周
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + HttpFrameConfig.NO_NETWORK_INVALID_TIME)
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .build();
        }
        return response;
    }
}
