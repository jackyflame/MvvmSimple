package com.jf.orr.net.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.jf.orr.net.entity.IResp;
import com.jf.orr.net.event.ErrorType;
import com.jf.orr.net.event.HttpEvent;
import com.jf.orr.net.http.TokenInterceptor;
import com.jf.orr.net.retrofit.INetWorker;
import com.jf.orr.net.retrofit.IRetrofitProvider;
import com.jf.orr.net.retrofit.ObserverReqCallback;
import com.jf.orr.net.retrofit.RetrofitHelper;
import com.jf.orr.net.retrofit.ReqCallback;
import com.jf.orr.net.retrofit.RetryWithDelay;
import com.jf.orr.log.NetLogUtil;
import com.jf.orr.utils.NetWorkUtil;
import com.jf.orr.utils.ToastUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @Class: BaseNetWorker
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/2/8
 */
public class BaseNetWorker implements INetWorker {

    private boolean isAlive;
    protected final LifecycleOwner mLifecycleOwner;
    private final CompositeDisposable subscription = new CompositeDisposable();

    public BaseNetWorker(LifecycleOwner mLifecycleOwner) {
        this.mLifecycleOwner = mLifecycleOwner;
        if(this.mLifecycleOwner != null){
            this.mLifecycleOwner.getLifecycle().addObserver(this);
        }
    }

    public <T> T createService(Class<T> clz) {
        return RetrofitHelper.getInstance().create(clz);
    }

    public <T> T createService(TokenInterceptor interceptor, Class<T> type){
        return RetrofitHelper.getInstance().create(interceptor, type);
    }

    public <T> T createService(String baseUrl, Class<T> type) {
        return RetrofitHelper.getInstance().create(baseUrl, type);
    }

    public <T> T createService(String baseUrl, TokenInterceptor interceptor, Class<T> type) {
        return RetrofitHelper.getInstance().create(baseUrl, interceptor, type);
    }

    public <T> T createService(IRetrofitProvider provider, Class<T> type) {
        return RetrofitHelper.getInstance().create(provider, type);
    }

    public <T,X extends IResp<T>> void defaultCall(@NonNull Observable<Response<X>> observable,
                                                   @Nullable ReqCallback<T> callback) {
        //检测网络是否连接，并且弹出提示框
        if (RetrofitHelper.getContext() != null && !NetWorkUtil.isConnectionAvailable(RetrofitHelper.getContext())) {
            HttpEvent netErrorEvent = new HttpEvent(ErrorType.ERROR_NETWORK_LOCAL,ErrorType.ERROR_NETWORK_MSG);
            ToastUtil.showShort(RetrofitHelper.getContext(),netErrorEvent.getMessage());
            if (callback != null) {
                callback.onReqError(netErrorEvent);
            }
            return;
        }
        subscription.add(observable
                .retryWhen(new RetryWithDelay(1, 3000))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(defaultCallback(callback)));
    }

    public void call(Disposable disposable) {
        subscription.add(disposable);
    }

    public <T> ObserverReqCallback<T> defaultCallback(ReqCallback<T> callback) {
        return new ObserverReqCallback<T>(this, callback);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(@NonNull LifecycleOwner owner) {
        NetLogUtil.i("BaseWorker", " LifecycleOwner[" + owner + "] ON_CREATE");
        this.isAlive = true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(@NonNull LifecycleOwner owner) {
        NetLogUtil.i("BaseWorker", "LifecycleOwner[" + owner + "] ON_DESTROY");
        this.isAlive = false;
        subscription.clear();
        if(this.mLifecycleOwner != null) {
            mLifecycleOwner.getLifecycle().removeObserver(this);
        }
    }

}
