package com.jf.orr.net.worker;

import com.jf.orr.base.BaseApplication;
import com.jf.orr.log.LogW;
import com.jf.orr.net.entity.RespEntity;
import com.jf.orr.net.event.ErrorType;
import com.jf.orr.net.event.HttpEvent;
import com.jf.orr.utils.NetWorkUtil;
import com.jf.orr.utils.RxBus;
import com.jf.orr.utils.StringUtil;

import java.io.IOException;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DisposableObserver;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @Discribe: com.vw.epls.http.retrofit
 * @Time: 2020/3/17
 * @Author: Yinhao
 */
public abstract class RequestCallbackSubscriber<T> extends DisposableObserver<Response<RespEntity<T>>> {

    protected boolean isPostToRxBus = true;

    @Override
    public void onComplete() {
        dispose();
    }

    @Override
    public void onError(Throwable e) {
        //包装错误信息
        final HttpEvent baseEvent = new HttpEvent();
        if (e instanceof IOException) {
            baseEvent.setCode(ErrorType.ERROR_NETWORK);
            baseEvent.setMessage("无数据网络，请开关WiFi或者移动网络");
        } else {
            LogW.e("RequestCallback error:" + e.getMessage());
            if(e instanceof HttpException){
                baseEvent.setCode(String.valueOf(((HttpException) e).code()));
            }else{
                baseEvent.setCode(ErrorType.ERROR_OTHER);
            }
            baseEvent.setMessage("系统繁忙，请重试");
        }
        //透传错误信息
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(NetWorkUtil.isConnectionAvailable(BaseApplication.getContext()));
            }
        }).subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    baseEvent.setCode(ErrorType.ERROR_NETWORK_SERVER);
                    baseEvent.setMessage("连接数据网络错误，请切换WiFi或者移动网络");
                }
                handleError(baseEvent);
            }
            @Override
            public void onError(Throwable e) {
                LogW.e("RequestCallback error:" + e.getMessage());
                dispose();
            }
            @Override
            public void onComplete() {
                dispose();
            }
        });
        //清理rx
        dispose();
    }

    @Override
    public void onNext(Response<RespEntity<T>> response) {
        RespEntity<T> respEntity = response.body();
        if (response.isSuccessful()) {
            if (respEntity == null) {
                handleError(new HttpEvent(ErrorType.ERROR_RESPONSE_NULL,"返回数据错误"));
            } else {
                if (respEntity.isSucceed()) {
                    onSuccess(respEntity.getTotalCount(),respEntity.getData());
                } else {
                    handleError(new HttpEvent(respEntity.getErrorCode(), respEntity.getErrorMessage()));
                }
            }
        } else {
            HttpEvent baseEvent;
            if (respEntity != null) {
                baseEvent = new HttpEvent(respEntity.getErrorCode()+"", respEntity.getErrorMessage());
            } else {
                baseEvent = new HttpEvent(response.code()+"", response.message());
            }
            handleError(baseEvent);
        }
    }

    private void handleError(HttpEvent httpEvent) {
        if (isPostToRxBus || StringUtil.isValueEqual(httpEvent.getCode(),ErrorType.ERROR_AUTH)) {
            RxBus.getInstance().post(httpEvent);
        }
        onError(httpEvent);
    }

    abstract public void onSuccess(int totalCount,T respEntity);

    abstract public void onError(HttpEvent baseEvent);
}
