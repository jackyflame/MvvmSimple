package com.jf.orr.net.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Class: BaseWorker
 * @Description:请求基类封装
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class RespEntity<T> extends RespBaseEntity {

    @JSONField(name = "data")
    private T data;
    private int totalCount;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
