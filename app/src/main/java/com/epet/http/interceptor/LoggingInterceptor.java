package com.epet.http.interceptor;

import android.util.Log;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;


/**
 * 作者：yuer on 2017/8/23 19:45
 * 邮箱：heziyu222@163.com
 */

public class LoggingInterceptor implements Interceptor {
    private static final String TAG="Okhttp";
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.d(TAG,String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.d(TAG,String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;

//        Request userRequest = chain.request();
//        long t1 = System.nanoTime();
//        Log.d(TAG,String.format("Sending request %s on %s%n%s",
//                userRequest.url(), chain.connection(), userRequest.headers()));
//        Request.Builder requestBuilder = userRequest.newBuilder();
//        Response networkResponse = chain.proceed(requestBuilder.build());
//        long t2 = System.nanoTime();
//        Log.d(TAG,String.format("Received response for %s in %.1fms%n%s",
//                networkResponse.request().url(), (t2 - t1) / 1e6d, networkResponse.headers()));
//        Response.Builder responseBuilder = networkResponse.newBuilder()
//                .request(userRequest);
//        if ("gzip".equalsIgnoreCase(networkResponse.header("Content-Encoding"))
//                && HttpHeaders.hasBody(networkResponse)) {
//            GzipSource responseBody = new GzipSource(networkResponse.body().source());
//            Headers strippedHeaders = networkResponse.headers().newBuilder()
//                    .build();
//            responseBuilder.headers(strippedHeaders);
//            responseBuilder.body(new RealResponseBody(strippedHeaders, Okio.buffer(responseBody)));
//        }
//        Response response = responseBuilder.build();
//        Log.d(TAG,response.body().string().toString());
//        return response;

    }


}
