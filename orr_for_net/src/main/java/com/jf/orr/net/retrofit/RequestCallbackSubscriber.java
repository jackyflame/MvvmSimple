package com.jf.orr.net.retrofit;

import com.jf.orr.net.entity.IResp;
import com.jf.orr.net.event.ErrorType;
import com.jf.orr.net.event.HttpEvent;
import com.jf.orr.log.NetLogUtil;
import com.jf.orr.utils.NetWorkUtil;

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
public abstract class RequestCallbackSubscriber<T> extends DisposableObserver<Response<? extends IResp<T>>> {

    @Override
    public void onComplete() {
        dispose();
    }

    @Override
    public void onError(Throwable e) {
        final HttpEvent baseEvent = new HttpEvent();
        if (e instanceof IOException) {
            baseEvent.setCode(ErrorType.ERROR_NETWORK);
            baseEvent.setMessage(ErrorType.ERROR_NETWORK_MSG);
        } else {
            NetLogUtil.e("RequestCallback error:" + e.getMessage());
            if(e instanceof HttpException){
                baseEvent.setCode(String.valueOf(((HttpException) e).code()));
            }else{
                baseEvent.setCode(ErrorType.ERROR_OTHER);
            }
            baseEvent.setMessage(ErrorType.ERROR_NETWORK_SERVER_MSG);
        }
        //分发错误
        handleError(baseEvent);
        //清理rx
        dispose();
    }

    private void checkNetWork(final HttpEvent baseEvent){
        if(RetrofitHelper.getContext() != null){
            //透传错误信息
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                    emitter.onNext(NetWorkUtil.isConnectionAvailable(RetrofitHelper.getContext()));
                }
            }).subscribe(new DisposableObserver<Boolean>() {
                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean) {
                        baseEvent.setCode(ErrorType.ERROR_NETWORK_SERVER);
                        baseEvent.setMessage(ErrorType.ERROR_NETWORK_MSG);
                    }
                    handleError(baseEvent);
                }
                @Override
                public void onError(Throwable e) {
                    NetLogUtil.e("RequestCallback error:" + e.getMessage());
                    dispose();
                }
                @Override
                public void onComplete() {
                    dispose();
                }
            });
        }
    }

    @Override
    public void onNext(Response<? extends IResp<T>> response) {
        IResp<T> respEntity = response.body();
        if (response.isSuccessful()) {
            if (respEntity == null) {
                handleError(new HttpEvent(ErrorType.ERROR_RESPONSE_NULL,ErrorType.ERROR_NETWORK_SERVER_MSG));
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
                baseEvent = new HttpEvent(respEntity.getErrorCode(), respEntity.getErrorMessage());
            } else {
                baseEvent = new HttpEvent(String.valueOf(response.code()), response.message());
            }
            handleError(baseEvent);
        }
    }

    private void handleError(HttpEvent httpEvent) {
        if(RetrofitHelper.getInterceptorList() != null){
            for(ReqInterceptor interceptor : RetrofitHelper.getInterceptorList()){
                //是否终止拦截
                if(interceptor.handleError(httpEvent)){
                    break;
                }
            }
        }
        onError(httpEvent);
    }

    abstract public void onSuccess(int totalCount,T respEntity);

    abstract public void onError(HttpEvent baseEvent);
}
