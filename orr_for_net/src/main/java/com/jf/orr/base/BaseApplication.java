package com.jf.orr.base;

import android.app.Application;
import android.content.Context;

/**
 * @Class: BaseApplication
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
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
