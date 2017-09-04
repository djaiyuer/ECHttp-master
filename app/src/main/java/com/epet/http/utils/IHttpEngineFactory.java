package com.epet.http.utils;

import com.epet.http.ECHttpClient;
import com.epet.http.imple.HttpEngineImple;
import com.epet.http.engine.RetrofitHttpEngine;
import com.epet.http.interfase.IHttpEngine;

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
        return  retrofitHttpFrame(builder);
    }

    /**
     * Retrofit框架的生产方法
     * @param builder
     * @return
     */
    private static IHttpEngine retrofitHttpFrame(ECHttpClient.Builder builder){
        HttpEngineImple httpEngineImple= new RetrofitHttpEngine.Builder()
                .setBaseUrl(builder.getmBaseUrl())
                .setUrl(builder.getmUrl())
                .setParam(builder.getmParams())
                .setConext(builder.getContext())
                .setFileKey(builder.getFileKey())
                .setFileList(builder.getFiles())
                .setSaveFilePath(builder.getSaveFilePath())
                .setSaveFileName(builder.getSaveFileName())
                .setOnResultListener(builder.getmListener())
                .builder();
        return httpEngineImple;
    }
}
