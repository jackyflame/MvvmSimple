package com.jf.orr.net.retrofit;

import com.jf.orr.net.event.HttpEvent;
import com.jf.orr.log.NetLogUtil;

public abstract class SimpleReqCallback<T> implements ReqCallback<T> {

    @Override
    public void onReqStart() {
        NetLogUtil.i("SimpleReqCallback","onReqStart!");
    }

    @Override
    public abstract void onNetResp(T response);

    @Override
    public void onReqError(HttpEvent httpEvent) {
        NetLogUtil.i("SimpleReqCallback","onReqError:"+httpEvent.getMessage());
    }
}
