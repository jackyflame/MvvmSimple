package com.jf.orr.net.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class RespEntity<T> extends RespBaseEntity<T>{

    @JSONField(name = "data")
    private T data;
    private int totalCount;
    private int total;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalCount() {
        if(total > 0){
            return total;
        }else{
            return totalCount;
        }
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
