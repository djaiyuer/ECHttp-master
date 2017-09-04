package com.epet.http;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.epet.http.utils.AESHelper;
import com.epet.http.utils.MPermissionUtils;

import java.io.File;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView content;
    private TextView upload;
    private TextView download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView httpGet = (TextView)findViewById(R.id.http_get) ;
        TextView httpPost = (TextView)findViewById(R.id.http_post) ;
        TextView httpPut = (TextView)findViewById(R.id.http_put) ;
        TextView httpDelete = (TextView)findViewById(R.id.http_delete) ;
        TextView httpGetCache = (TextView)findViewById(R.id.http_get_cache) ;
        upload = (TextView)findViewById(R.id.upload) ;
        download = (TextView)findViewById(R.id.download) ;
        content = (TextView)findViewById(R.id.content);
        httpGet.setOnClickListener(this);
        httpPost.setOnClickListener(this);
        httpPut.setOnClickListener(this);
        httpDelete.setOnClickListener(this);
        upload.setOnClickListener(this);
        download.setOnClickListener(this);
        httpGetCache.setOnClickListener(this);
//        new ECHttpClient.Builder()
//                .setBaseUrl("https://mallcdn.api.epet.com/")
//                .setUrl("v3/index/main.html")
//                .setContext(this)
//                .builder().httpGet();
//
//        new ECHttpClient.Builder()
//                .setBaseUrl("https://mall.api.epet.com/")
//                .setUrl("v3/index/main.html?do=getDynamicV315")
//                .setContext(this)
//                .builder().httpGet();
    }

    private void checkPermission(){
        MPermissionUtils.requestPermissionsResult(this, 1, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                new ECHttpClient.Builder()
                        .setBaseUrl("https://wap.epet.com/")
                        .setUrl("download.html?appname=epetmall&system=android")
                        .setSaveFileName("epetmall.apk")
                        .setContext(MainActivity.this)
                        .builder().downLoad();
            }

            @Override
            public void onPermissionDenied() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.http_get:
                new ECHttpClient.Builder()
                        .setBaseUrl("https://www.sojson.com/")
                        .setUrl("open/api/weather/json.shtml")
                        .setParam("city","重庆")
                        .setContext(this)
                        .setOnResultListener(new OnResultListener(){
                            @Override
                            public void onSuccess(String result) {
                                super.onSuccess(result);
                                content.setText(result);
                            }

                            @Override
                            public void onFailure(String message) {
                                super.onFailure(message);
                            }
                        })
                        .builder().httpGet();
                break;
            case R.id.http_post:
                new ECHttpClient.Builder()
                .setBaseUrl("http://mall.api.dev.epet.com/")
                .setUrl("v3/login.html?do=postSubmit")
                 .setParam("username", AESHelper.Encrypt("何子瑜"))
                 .setParam("password",AESHelper.Encrypt("a147258369;"))
                 .setParam("debug","1")
                 .setParam("postsubmit","r9b8s7m4")
                .setContext(this)
                .setOnResultListener(new OnResultListener(){
                    @Override
                    public void onSuccess(String result) {
                        super.onSuccess(result);
                        content.setText(result);
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                    }
                })
                .builder().httpPost();
                break;
            case R.id.http_put:

                break;
            case R.id.http_delete:

                break;
            case R.id.http_get_cache:
                new ECHttpClient.Builder()
                        .setBaseUrl("https://www.sojson.com/")
                        .setUrl("open/api/weather/json.shtml")
                        .setParam("city","重庆")
                        .setContext(this)
                        .setOnResultListener(new OnResultListener(){
                            @Override
                            public void onSuccess(String result) {
                                super.onSuccess(result);
                                content.setText(result);
                            }

                            @Override
                            public void onFailure(String message) {
                                super.onFailure(message);
                            }
                        })
                        .builder().httpGet();
                break;
            case R.id.upload:
                File file1=new File("/storage/emulated/0/DCIM/Camera/IMG_20170804_211740.jpg");
                ArrayList<File> files = new ArrayList<>();
                files.add(file1);
                new ECHttpClient.Builder()
                        .setBaseUrl("http://mall.api.dev.epet.com/")
                        .setUrl("v3/upload.html")
                        .setParam("debug","1")
                        .setParam("postsubmit","r9b8s7m4")
                        .setFileKey("file")
                        .setFileList(files)
                        .setOnResultListener(new OnResultListener(){
                            @Override
                            public void uploadProgress(String filePath, long currentProgress, long totalProgress) {
                                super.uploadProgress(filePath, currentProgress, totalProgress);
                                int pro = (int) (currentProgress * 100 / totalProgress);
                                upload.setText("UPLOAD  "+pro+"%");
                            }

                            @Override
                            public void uploadComplate(String filePath) {
                                super.uploadComplate(filePath);
                            }

                            @Override
                            public void onSuccess(String result) {
                                super.onSuccess(result);
                                content.setText(result);
                            }
                        })
                        .setContext(MainActivity.this)
                        .builder().upload();
                break;
            case R.id.download:
                new ECHttpClient.Builder()
                        .setBaseUrl("http://p0.jmstatic.com/")
                        .setUrl("mobile/package/exchange/android/jumeilatest.apk")
                        .setSaveFileName("jumeilatest.apk")
                        .setContext(MainActivity.this)
                        .setOnResultListener(new OnResultListener(){
                            @Override
                            public void downLoadProgress(long currentProgress, long totalProgress) {
                                super.downLoadProgress(currentProgress, totalProgress);
                                int pro = (int) (currentProgress * 100 / totalProgress);
                                download.setText("DOWNLOAD  "+pro+"%");
                            }

                            @Override
                            public void downLoadComplate(String filePath) {
                                super.downLoadComplate(filePath);
                                download.setText("DOWNLOAD  存储位置"+filePath);
                            }

                            @Override
                            public void onSuccess(String result) {
                                super.onSuccess(result);

                            }

                            @Override
                            public void onFailure(String message) {
                                super.onFailure(message);
                            }
                        })
                        .builder().downLoad();
                break;
        }
    }
}
