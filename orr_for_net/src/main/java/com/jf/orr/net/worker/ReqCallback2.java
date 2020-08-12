package com.jf.orr.net.worker;

/**
 * @Discribe: com.vw.epls.http.retrofit
 * @Time: 2020/3/17
 * @Author: Yinhao
 */
public interface ReqCallback2<T> extends ReqCallback<T>{

    void onNetResp(int totalCount, T response);

}

