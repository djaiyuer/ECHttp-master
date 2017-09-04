package com.epet.http;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.epet.http.config.HttpFrameConfig;
import com.epet.http.utils.LogUtil;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.BuildConfig;

/**
 * 作者：yuer on 2017/8/16 15:38
 * 邮箱：heziyu222@163.com
 */

public class HttpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Stetho.initialize(Stetho
//                .newInitializerBuilder(this)
//                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                .enableWebKitInspector(
//                        Stetho.defaultInspectorModulesProvider(this)).build());
        Stetho.initializeWithDefaults(this);
        LogUtil.init(BuildConfig.DEBUG);
//        HttpFrameConfig.httpsCertificateMaps.put("https://wap.epet.com/","https/epet.cer");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
