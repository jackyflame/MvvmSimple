package com.jf.orr.utils;


import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle4.LifecycleProvider;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * Created by Android Studio.
 * ProjectName: shenbian_android_cloud_speaker
 * Author:  HAOZI
 * Date: 4/09/19
 * Time: 10:01 AM
 */
public class RxBus {

    private Subject<Object> mBus;

    private static class InstanceHolder{
        private static final RxBus INSTANCE = new RxBus();
    }

    public static RxBus getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public RxBus() {
        this.mBus = PublishSubject.create().toSerialized();
    }

    /**
     * 发送事件
     */
    public void post(@NonNull Object event) {
        mBus.onNext(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> asObservable(LifecycleOwner owner, final Class<T> eventType) {
        if(owner == null){
            return mBus.ofType(eventType);
        }
        LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(owner);
        return mBus.ofType(eventType).compose(provider.<T>bindToLifecycle());
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

}
