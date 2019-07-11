package com.epet.http.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 作者：yuer on 2017/8/31 17:18
 * 邮箱：heziyu222@163.com
 */

public class FileUtil {
    /**
     * 检查sd卡状态
     */
    public static boolean checkSDStatus() {
        //判断sd是否可用
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED))
        {
            return false;
        }
        return true;
    }


    /**
     * 获得下载保存默认地址
     */
    public static String getDefaultDownLoadPath(Context context) {
        if (checkSDStatus()){
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+ File.separator;
        }
        return context.getFilesDir().getAbsolutePath();
    }


    /**
     * 从url中，获得默认文件名
     */
    public static String getDefaultDownLoadFileName(String url) {
        if (url == null || url.length() == 0) {
            return "";
        }
        int nameStart = url.lastIndexOf('/')+1;
        return url.substring(nameStart);
    }

}
