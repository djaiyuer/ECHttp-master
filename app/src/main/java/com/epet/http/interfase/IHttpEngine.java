package com.epet.http.interfase;

/**
 * 抽象产品角色：网络引擎
 * 作者：yuer on 2017/8/17 11:10
 * 邮箱：heziyu222@163.com
 */

public interface IHttpEngine {
    /**
     *  GET 需要支持缓存策略
     */
    void httpGet();

    /**
     * POST
     */
    void httpPost();

    /**
     * PUT
     */
    void httpPut();

    /**
     * DELETE
     */
    void httpDelete();

    /**
     * 上传
     */
    void upload();

    /**
     * 下载
     */
    void download();


}
