package com.epet.http.listener;

/**
 * 作者：yuer on 2017/8/21 20:38
 * 邮箱：heziyu222@163.com
 */

public interface ProgressListener {
    public void onProgress(String filePath,long writtenBytesCount, long totalBytesCount,boolean isFinish);
}
