package com.epet.http.abstrac;

/**
 * 请求下载回调抽象类
 * 作者：yuer on 2017/8/28 13:58
 * 邮箱：heziyu222@163.com
 */

public abstract class HttpProgressOnNextListener<T> {
    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 成功后回调
     * @param t
     */
    public abstract void onNext(T t);

    /**
     * 完成下载
     */
    public abstract void onComplate();

    /**
     * 下载进度
     * @param currentByte
     * @param totalByte
     */
    public abstract void onProgresss(long currentByte,long totalByte);

    /**
     * 暂停
     */
    public  void onPause(){

    }

    /**
     *停止下载
     */
    public  void onStop(){

    }

    /**
     * 下载错误
     */
    public void onError(Throwable e){

    }
}
