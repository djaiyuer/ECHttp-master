package com.epet.http.utils;

import com.epet.http.ECHttpClient;
import com.epet.http.engine.RetrofitHttpEngine;
import com.epet.http.imple.HttpEngineImple;
import com.epet.http.listener.IHttpEngine;

/**
 * 构建具体的网络框架
 * 作者：yuer on 2017/8/17 14:55
 * 邮箱：heziyu222@163.com
 */

public class IHttpEngineFactory {
    /**
     * 根据编号生产集体的网络框架
     * @param builder 请求相关参数实体
     * @return
     */
    public static IHttpEngine createHttpEngine(ECHttpClient.Builder builder){
        return new RetrofitHttpEngine(createHttpBuilder(builder));
    }

    private static HttpEngineImple.Builder createHttpBuilder(ECHttpClient.Builder builder){
        HttpEngineImple.Builder httpBuilder =  new HttpEngineImple.Builder()
                .setBaseUrl(builder.getBaseUrl())
                .setUrl(builder.getUrl())
                .setParam(builder.getParams())
                .setFileKey(builder.getFileKey())
                .setFileList(builder.getFiles())
                .setSaveFilePath(builder.getSaveFilePath())
                .setSaveFileName(builder.getSaveFileName())
                .setOnResultListener(builder.getListener())
                .addInterceptors(builder.getInterceptors());
        return  httpBuilder;
    }
}
