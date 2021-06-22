package com.jf.orr.net.retrofit;

import com.jf.orr.net.event.HttpEvent;

/**
 * @Class: ReqInterceptor
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/2/8
 */
public interface ReqInterceptor {

    boolean handleError(HttpEvent httpEvent);

}
