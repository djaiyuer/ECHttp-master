package com.epet.http.listener;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者：yuer on 2017/8/17 15:49
 * 邮箱：heziyu222@163.com
 */

public interface IHttpService {
    @GET
    Observable<ResponseBody> requestGet (@Url String url, @QueryMap Map<String,String> param);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> requestPost(@Url String url, @FieldMap Map<String,String> param);

    @PUT
    Observable<ResponseBody> requestPut(@Url String url,  @FieldMap Map<String,String> param);

    @DELETE
    Observable<ResponseBody> requestDelete(@Url String url, @FieldMap Map<String,String> param);

    /**
     * 上传
     * @param url 上传请求路径
     * @param parts 多图
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody>  upload(@Url String url, @Part() List<MultipartBody.Part> parts);


    /**
     * 下载
     * @param url
     * @return
     */

    @Streaming /*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String url);
}
