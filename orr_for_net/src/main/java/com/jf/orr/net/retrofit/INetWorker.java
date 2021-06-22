package com.jf.orr.net.retrofit;

import androidx.lifecycle.LifecycleObserver;

/**
 * @Class: INetHandler
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/2/8
 */
public interface INetWorker extends LifecycleObserver {

    boolean isAlive();

}
