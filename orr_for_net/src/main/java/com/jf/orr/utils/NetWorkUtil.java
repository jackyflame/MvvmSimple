package com.jf.orr.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jf.orr.log.LogW;

/**
 * @Discribe: com.vw.base.utils
 * @Time: 2020/3/17
 * @Author: Yinhao
 */
public class NetWorkUtil {

    //判断是否有网
    public static boolean NetState(Context context, String showStr) {
        boolean flag = false;
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectManager != null && connectManager.getActiveNetworkInfo() != null) {
            flag = connectManager.getActiveNetworkInfo().isAvailable();
            //如果没有网络跳到网络设置界面
            if (!flag) {
                ToastUtil.showShort(showStr);
            }
        } else {
            ToastUtil.showShort(showStr);
        }

        return flag;
    }

    public boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectManager.getActiveNetworkInfo();
        return null != info && info.isConnected();
    }

    public static boolean isConnectionAvailable(Context cotext) {
        boolean isConnectionFail = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) cotext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                isConnectionFail = true;
            }
        } else {
            LogW.e("Can't get connectivitManager");
        }
        return isConnectionFail;
    }

}
