package com.jf.orr.log;

import android.util.Log;

/**
 * @Class: NetLogUtil
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/2/7
 */
public class NetLogUtil {

    public static final String TAG = "NET-ORR";
    public static boolean isDebug = true;

    public static void d(String subtitle, String msg) {
        d(subtitle+">>>"+msg);
    }

    public static void d(String msg){
        if(!isDebug){
            return;
        }
        Log.d(TAG, msg);
    }

    public static void i(String subtitle, String msg) {
        i(subtitle+">>>"+msg);
    }

    public static void i(String msg) {
        if(!isDebug){
            return;
        }
        Log.i(TAG, msg);
    }

    public static void e(String subtitle, String msg) {
        e(subtitle+">>>"+msg);
    }

    public static void e(String msg) {
        if(!isDebug){
            return;
        }
        Log.e(TAG, msg);
    }


}
