package com.jf.orr.net.retrofit;

import com.jf.orr.net.event.HttpEvent;

/**
 * Created by Android Studio.
 * ProjectName: vw
 * Author: yh
 * Date: 2019/3/17
 * Time: 15:05
 */
public class ObserverReqCallback<T> extends RequestCallbackSubscriber<T> {

    private INetWorker worker;
    private ReqCallback<T> callback;

    public ObserverReqCallback(INetWorker worker, ReqCallback<T> callback) {
        this.worker = worker;
        this.callback = callback;
    }

    @Override
    public void onSuccess(int totalCount,T respEntity) {
        if (callback != null && worker.isAlive()){
            callback.onNetResp(respEntity);
            if(callback instanceof ReqCallback2){
                ((ReqCallback2<T>) callback).onNetResp(totalCount,respEntity);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callback != null && worker.isAlive())
            callback.onReqStart();
    }

    @Override
    public void onError(HttpEvent baseEvent) {
        if (callback != null && worker.isAlive())
            callback.onReqError(baseEvent);
    }

}