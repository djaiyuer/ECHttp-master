package com.epet.http.config;

import java.util.HashMap;

/**
 * 作者：yuer on 2017/8/17 14:57
 * 邮箱：heziyu222@163.com
 */

public class HttpFrameConfig {
    //连接超时时间
    public static final long TIMEOUT_CONNECTION = 15;
    //读取时间
    public static final long TIMEOUT_READ = 20;
    //写入时间
    public static final long TIMEOUT_WRITE = 20;
    //硬盘缓存大小
    public static final int HTTP_DISK_CACHE_SIZE = 1024 * 1024 * 50;
    //无网络时缓存失效时间 1周
    public static final int NO_NETWORK_INVALID_TIME = 60 * 60 * 24 * 7;

    public static HashMap<String,String> httpsCertificateMaps = new HashMap<>();
    public void initHttpsCertificateMaps(HashMap<String,String> certificateMaps ){
        if(certificateMaps!=null && !certificateMaps.isEmpty()){
            httpsCertificateMaps.clear();
            httpsCertificateMaps.putAll(certificateMaps);
        }
    }

}
