package com.jf.orr.net.retrofit;

/**
 * @Discribe: com.vw.epls.http.retrofit
 * @Time: 2020/3/17
 * @Author: github.com/jackyflame
 */
public interface ReqCallback2<T> extends ReqCallback<T>{

    void onNetResp(int totalCount, T response);

}

