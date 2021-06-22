package com.jf.orr.log;

import android.util.Log;

/**
 * @Class: LogW
 * @Description: LogW日志封装器
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class LogW {

    public static String TAG = "NET-ORR";
    public static boolean isDebug = true;

    public static void i(String msg){
        if(isDebug){
            Log.i(TAG,msg);
        }
    }

    public static void i(Object obj, String msg){
       i(obj.getClass(),msg);
    }

    public static void i(Class cls, String msg){
        i(cls.getSimpleName(),msg);
    }

    public static void i(String title, String msg){
        if(isDebug) {
            Log.i(TAG, title + " >>> " + msg);
        }
    }

    public static void d(String msg){
        if(isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void d(Object obj, String msg){
        d(obj.getClass(),msg);
    }

    public static void d(Class cls, String msg){
        d(cls.getSimpleName(),msg);
    }

    public static void d(String title, String msg){
        if(isDebug) {
            Log.d(TAG, title + " >>> " + msg);
        }
    }

    public static void e(String msg){
        if(isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void e(Object obj, String msg){
        e(obj.getClass(),msg);
    }

    public static void e(Class cls, String msg){
        e(cls.getSimpleName(), msg);
    }

    public static void e(String title, String msg){
        if(isDebug) {
            Log.e(TAG, title + " >>> " + msg);
        }
    }

}
