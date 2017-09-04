package com.epet.http.interfase;

/**
 * 抽象产品角色：网络引擎
 * 作者：yuer on 2017/8/17 11:10
 * 邮箱：heziyu222@163.com
 */

public interface IHttpEngine {
    // GET 需要支持缓存策略
    public void httpGet();
    // POST
    public void httpPost();
    // PUT
    public void httpPut();
    // DELETE
    public void httpDelete();

    public void upload();

    public void download();


}
