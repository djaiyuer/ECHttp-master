package com.epet.http.imple;

import android.content.Context;
import android.util.Log;

import com.epet.http.OnResultListener;
import com.epet.http.engine.RetrofitHttpEngine;
import com.epet.http.entity.DownInfoEntity;
import com.epet.http.interfase.IHttpEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * 作者：yuer on 2017/8/18 17:20
 * 邮箱：heziyu222@163.com
 */

public class HttpEngineImple implements IHttpEngine {
    public HttpEngineImple.Builder mBuidler;
    private Context mConext;
    public HttpEngineImple(){}
    public HttpEngineImple(HttpEngineImple.Builder builder) {
        this.mBuidler = builder;
        this.mConext = builder.mConext;
    }
    @Override
    public void httpGet() {
        Log.d(HttpEngineImple.class.getSimpleName(),"执行的我");
    }

    @Override
    public void httpPost() {

    }

    @Override
    public void httpPut() {

    }

    @Override
    public void httpDelete() {

    }

    @Override
    public void upload() {

    }

    @Override
    public void download() {

    }

    public static class Builder {
        //域
        private String mBaseUrl;
        //请求接口
        private String mUrl;
        //参数集合
        private HashMap<String, String> mParam = new HashMap<>();
        //上传文件参数名
        private String mFileKey;
        //文件列表
        private ArrayList<File> mFiles;
        //请求回执接口
        private OnResultListener mListener;
        private Context mConext;
        private DownInfoEntity mDownLoadInfo;
        public HttpEngineImple.Builder setBaseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        public HttpEngineImple.Builder setUrl(String url) {
            this.mUrl = url;
            return this;
        }

        public HttpEngineImple.Builder setParam(HashMap<String, String> param) {
            mParam.clear();
            if (param != null)
                mParam.putAll(param);
            return this;
        }

        public HttpEngineImple.Builder setFileKey(String fileKey){
            this.mFileKey = fileKey;
            return this;
        }

        public HttpEngineImple.Builder setFileList(ArrayList<File> files){
            this.mFiles = files;
            return this;
        }



        public HttpEngineImple.Builder setConext(Context conext) {
            this.mConext = conext;
            return this;
        }

        public HttpEngineImple.Builder setOnResultListener(OnResultListener mListener) {
            this.mListener = mListener;
            return this;
        }

        public HttpEngineImple.Builder setSaveFilePath(String filePath){
            createDownInfoEntity();
            this.mDownLoadInfo.setSavePath(filePath);
            return this;
        }
        public HttpEngineImple.Builder setSaveFileName(String fileName){
            createDownInfoEntity();
            this.mDownLoadInfo.setSaveFileName(fileName);
            return this;
        }

        public Context getConext() {
            return mConext;
        }

        public String getBaseUrl() {
            return mBaseUrl;
        }

        public String getUrl() {
            return mUrl;
        }

        public OnResultListener getListener() {
            return mListener;
        }

        public HashMap<String, String> getParam() {
            return mParam;
        }

        public ArrayList<File> getFiles() {
            return mFiles;
        }

        public String getFileKey() {
            return mFileKey;
        }

        public DownInfoEntity getDownLoadInfo() {
            return mDownLoadInfo;
        }

        private void createDownInfoEntity(){
            if(this.mDownLoadInfo==null){
                this.mDownLoadInfo = new DownInfoEntity();
            }
        }

        //这里动态返回子类对象进行框架的切换
        public HttpEngineImple builder() {
            return new RetrofitHttpEngine(this);
        }
    }
}
