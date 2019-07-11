package com.epet.http;

import android.content.Context;

import com.epet.http.interceptor.BaseInterceptor;
import com.epet.http.utils.IHttpEngineFactory;
import com.epet.http.interfase.IHttpEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 网络请求客户端
 * 作者：yuer on 2017/8/16 17:24
 * 邮箱：heziyu222@163.com
 */

public class ECHttpClient {
    /**
     * 构建对象
     */
    private Builder mBuilder;

    private IHttpEngine mHttpEngine;
    /**
     * 构造函数
     * @param builder
     */
    private ECHttpClient(Builder builder){
        this.mBuilder = builder;
        this.mHttpEngine = IHttpEngineFactory.createHttpEngine(mBuilder);
    }
    public void httpGet() {
        this.mHttpEngine.httpGet();
    }
    public void httpPost() {
        this.mHttpEngine.httpPost();
    }
    public void httpPut() {
        this.mHttpEngine.httpPut();
    }
    public void httpDelete() {
        this.mHttpEngine.httpDelete();
    }
    public void upload(){
        this.mHttpEngine.upload();
    }
    public void downLoad(){
        this.mHttpEngine.download();
    }
    /**
     * 构建者：用于构建ECHttpClient对象
     */
    public static class Builder{
        /**
         * 网络请求的域：改属性需要支持动态设置，根据不同的业务场景会有不同的域
         */
        private String mBaseUrl;
        /**
         * 接口地址
         */
        private String mUrl;
        /**
         * 上传文件参数名
         */
        private String mFileKey;
        /**
         * 文件列表
         */
        private ArrayList<File> mFiles;
        private String mSaveFilePath;
        private String mSaveFileName;
        /**
         * 请求参数
         */
        private HashMap<String , String> mParams = new HashMap<>();
        private List<BaseInterceptor> mInterceptors;
        private OnResultListener mListener;
        public Builder(){

        }
        /**
         * 设置域
         * @param baseUrl 域
         */
        public Builder setBaseUrl(String baseUrl){
            this.mBaseUrl = baseUrl;
            return this;
        }

        /**
         * 设置接口地址
         * @param url
         */
        public Builder setUrl(String url){
            this.mUrl = url;
            return this;
        }

        /**
         * 设置参数
         * @param key 参数名
         * @param value 参数值
         */
        public Builder setParam(String key , String value){
            if(!this.mParams.containsKey(key)){
                this.mParams.put(key,value);
            }
            return this;
        }

        /**
         * 添加拦截器
         * @param interceptor
         * @return
         */
        public Builder addInterceptors(BaseInterceptor interceptor){
            if(this.mInterceptors == null){
                this.mInterceptors = new ArrayList<>();
            }
            this.mInterceptors.add(interceptor);
            return this;
        }


        public List<BaseInterceptor> getInterceptors() {
            return mInterceptors;
        }

        /**
         *  设置参数 ：如果当参数过多时可以构建map参数集合进行请求
         * @param params 参数集合
         * @return
         */
        public Builder setParam(HashMap<String , String> params){
            if(params!=null && !params.isEmpty()){
                this.mParams.clear();
                this.mParams.putAll(params);
            }
            return this;
        }

        public Builder setFileKey(String fileKey){
            this.mFileKey = fileKey;
            return this;
        }

        public Builder setFileList(ArrayList<File> files){
            this.mFiles = files;
            return this;
        }
        public Builder setOnResultListener(OnResultListener listener){
            this.mListener = listener;
            return this;
        }

        public Builder setSaveFilePath(String mSaveFilePath) {
            this.mSaveFilePath = mSaveFilePath;
            return this;
        }

        public Builder setSaveFileName(String mSaveFileName) {
            this.mSaveFileName = mSaveFileName;
            return this;
        }

        public String getSaveFilePath() {
            return this.mSaveFilePath;
        }

        public String getSaveFileName() {
            return this.mSaveFileName;
        }

        /**
         * 构建方法：调用该方法得到ECHttpClient
         * @return
         */
        public ECHttpClient builder(){
            return new ECHttpClient(this);
        }

        public String getBaseUrl() {
            return this.mBaseUrl;
        }

        public String getUrl() {
            return this.mUrl;
        }

        public HashMap<String, String> getParams() {
            return this.mParams;
        }

        public ArrayList<File> getFiles() {
            return this.mFiles;
        }

        public String getFileKey() {
            return this.mFileKey;
        }
        public OnResultListener getListener() {
            return this.mListener;
        }
    }
}
