package com.epet.http.entity;

import com.epet.http.abstrac.HttpProgressOnNextListener;
import com.epet.http.config.DownState;
import com.epet.http.interfase.IHttpService;

/**
 * 作者：yuer on 2017/8/28 14:17
 * 邮箱：heziyu222@163.com
 */

public class DownInfoEntity {
    //存储位置
    private String savePath;

    private String saveFileName;
    //下载长度
    private long readLength;
    //文件总长度
    private long totalLength;
    //回调接口
    private HttpProgressOnNextListener listener;
    //超时时间
    private int timeOut = 6;
    //状态
    private DownState downState;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public HttpProgressOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpProgressOnNextListener listener) {
        this.listener = listener;
    }


    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public DownState getDownState() {
        return downState;
    }

    public void setDownState(DownState downState) {
        this.downState = downState;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }
}
