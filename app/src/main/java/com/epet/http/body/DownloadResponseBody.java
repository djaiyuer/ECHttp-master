package com.epet.http.body;

import com.epet.http.listener.DownLoadingProgressListener;

import java.io.IOException;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 下载请求回执
 * 作者：yuer on 2017/8/28 11:24
 * 邮箱：heziyu222@163.com
 */

public class DownloadResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private DownLoadingProgressListener progressListener;
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody, DownLoadingProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            long bytesRead = 0l;
            @Override
            public long read(Buffer sink, long byteCount) {
                try {
                     bytesRead = super.read(sink, byteCount);
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    Observable.just(bytesRead).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (null != progressListener) {
                                progressListener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                return bytesRead;
            }
        };

    }
}
