package com.jf.orr.net.entity;

/**
 * @Class: IResp
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/02/08
 */
public interface IResp<T> {

    boolean isSucceed();

    String getErrorMessage();

    String getErrorCode();

    T getData();

    int getTotalCount();
}
