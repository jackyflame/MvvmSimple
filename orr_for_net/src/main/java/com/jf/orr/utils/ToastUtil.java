package com.jf.orr.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.jf.orr.base.BaseApplication;

/**
 * @Class: ToastUtil
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class ToastUtil {

    public static void showShort(String message){
        show(BaseApplication.getContext(),message,Toast.LENGTH_SHORT);
    }

    public static void showLong(String message){
        show(BaseApplication.getContext(),message,Toast.LENGTH_LONG);
    }

    /**
     * show toast message after action executed.
     * @param context
     * @param message
     * @param duration : Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    public static void show(Context context, String message, int duration) {
        if (context == null) {
            return;
        }
        Toast mToast = Toast.makeText(context,message,duration);
        mToast.setGravity(Gravity.BOTTOM, 0, 140);
        mToast.show();
    }

}
