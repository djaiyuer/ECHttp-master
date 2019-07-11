package com.epet.http;

/**
 * 网络请求回调接口
 * 作者：yuer on 2017/8/17 16:27
 * 邮箱：heziyu222@163.com
 */

public abstract class OnResultListener<T> {
    /**
     * 网络异常回调接口
     * @param e 异常
     */
    public void onNetworkError(Throwable e){

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
     * 失败回执
     * @param message
     */
    public void onFailure(T message){

    }



    /**
     * 请求完毕
     */
    public void onFinish(){

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
     * @param filePath
     */
    public void downLoadComplate(String filePath){

    }

}
