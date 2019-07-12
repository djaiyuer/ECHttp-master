package com.epet.http.listener;

/**
 * 网络请求回调接口
 * 作者：yuer on 2017/8/17 16:27
 * 邮箱：heziyu222@163.com
 */

public abstract class OnResultListener<T> {
    /**
     * 发起请求
     */
    public void onRequestStart(){

    }

    /**
     * 网络异常回调接口
     * @param isNetWorkAvailable 网络是否可用
     * @param e 异常提示
     */
    public void onNetworkError(boolean isNetWorkAvailable , String e){

    }

    /**
     * 成功回执
     * @param result 字符串
     */
    public void onSuccess(String result){

    }

    /**
     * 成功回执
     * @param result 对象
     */
    public void onSuccess(T result){

    }

    /**
     * 成功回执-緩存
     * @param result 对象
     */
    public void onCache(T result){

    }

    /**
     * 成功回执-緩存
     * @param result 字符串
     */
    public void onCache(String result){

    }
    /**
     * 失败回执
     * @param message
     */
    public void onFailure(T message){

    }



    /**
     * 请求完毕
     */
    public void onRequestFinish(){

    }

    /**
     * 上传中
     * @param filePath 图片本地地址
     * @param currentProgress 当前进度
     * @param totalProgress 文件总长度
     */
    public void uploadProgress(String filePath, long currentProgress, long totalProgress){

    }

    /**
     * 上传完成
     * @param filePath
     */
    public void uploadComplate(String filePath){

    }

    /**
     * 下载中
     * @param currentProgress 当前进度
     * @param totalProgress 文件总长度
     */
    public void downLoadProgress(long currentProgress, long totalProgress){

    }

    /**
     * 下载完成
     * @param filePath 下载文件保存地址
     * @param fileName 下载文件保存名
     */
    public void downLoadComplate(String filePath , String fileName){

    }

}
