package com.jf.orr.net.retrofit;

import com.jf.orr.net.event.HttpEvent;

/**
 * @Discribe: com.vw.epls.http.retrofit
 * @Time: 2020/3/17
 * @Author: github.com/jackyflame
 */
public interface ReqCallback<T> {

    void onReqStart();

    void onNetResp(T response);

    void onReqError(HttpEvent httpEvent);
}

