package com.jf.orr.net.worker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.jf.orr.base.BaseApplication;
import com.jf.orr.log.LogW;
import com.jf.orr.net.entity.RespEntity;
import com.jf.orr.net.event.ErrorType;
import com.jf.orr.net.event.HttpEvent;
import com.jf.orr.utils.NetWorkUtil;
import com.jf.orr.utils.ToastUtil;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @Class: BaseWorker
 * @Description:请求基类封装
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public abstract class BaseWorker implements LifecycleObserver {

    private AtomicBoolean isAlive;
    //订阅管理类
    private CompositeDisposable subscription = new CompositeDisposable();

    protected <T> void defaultCall(@NonNull Observable<Response<RespEntity<T>>> observable, @Nullable ReqCallback<T> callback) {
        defaultCall(observable, callback, true, true);
    }

    protected <T> void defaultCall(@NonNull Observable<Response<RespEntity<T>>> observable,
                                   @Nullable ReqCallback<T> callback, boolean isPostToRxBus, boolean isCheckNetWork) {
        //检测网络是否连接，并且弹出提示框
        if (isCheckNetWork && !NetWorkUtil.isConnectionAvailable(BaseApplication.getContext())) {
            HttpEvent netErrorEvent = new HttpEvent(ErrorType.ERROR_NETWORK_LOCAL,
                    "无数据网络，请开关WiFi或者移动网络");
            ToastUtil.showShort(netErrorEvent.getMessage());
            if (callback != null) {
                callback.onReqError(netErrorEvent);
            }
            return;
        }
        subscription.add(observable
                .retryWhen(new RetryWithDelay(1, 3000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(defaultCallback(callback, isPostToRxBus)));
    }

    private <T> WorkerCallback<T> defaultCallback(ReqCallback<T> callback, boolean isPostToRxBus) {
        return new WorkerCallback<>(this, callback, isPostToRxBus);
    }

    /*-----------------------------生命周期监测----------------------------------*/
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onStop(@NonNull LifecycleOwner owner) {
        LogW.i(this, " LifecycleOwner[" + owner + "] ON_CREATE");
        this.isAlive.set(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(@NonNull LifecycleOwner owner) {
        LogW.i(this, "LifecycleOwner[" + owner + "] ON_DESTROY");
        this.isAlive.set(false);
        subscription.clear();
        onViewDetach();
    }

    protected boolean isAlive() {
        return isAlive.get();
    }

    protected abstract void onViewDetach();
}
