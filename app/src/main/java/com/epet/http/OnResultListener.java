package com.epet.http;

/**
 * 网络请求回调接口
 * 作者：yuer on 2017/8/17 16:27
 * 邮箱：heziyu222@163.com
 */

public class OnResultListener<T> {
    /**
     * 缓存字符串
     * @param cache
     * @return
     */
    public boolean onCache(String cache){
        return false;
    }

    /**
     * 缓存对象
     * @param cache
     * @return
     */
    public boolean onCache(T cache){
        return false;
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
     * @param message 字符串
     */
    public void onFailure(String message){

    }


    /**
     * 失败回执
     * @param cache 缓存字符串
     * @param message 错误信息
     */
    public void onFailure(String cache,String message){

    }


    /**
     * 失败回执
     * @param cache 缓存对象
     * @param message 错误信息
     */
    public void onFailure(T cache,String message){

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
