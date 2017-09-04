package com.epet.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：yuer on 2017/8/16 11:29
 * 邮箱：heziyu222@163.com
 */

public interface RetrofitService {
    @GET("iplookup/iplookup.php?format=js")
    Call<ResponseBody> ipAddess(@Query("ip") String ip);
}
