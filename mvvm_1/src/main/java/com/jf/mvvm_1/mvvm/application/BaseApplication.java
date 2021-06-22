package com.jf.mvvm_1.mvvm.application;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    public static BaseApplication mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = BaseApplication.this;
    }

    public static Context getContext() {
        return mContext;
    }
}
