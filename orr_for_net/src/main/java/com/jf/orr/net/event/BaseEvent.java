/*
 * Copyright (c) 2016 ShenBianVip, Ltd.
 * Unauthorized copying of this file, via any medium is strictly prohibited proprietary and
 *  confidential.
 * Created on 1/4/16 4:59 PM
 * ProjectName: shenbian_android_cloud_speaker ; ModuleName: CSpeakerLib ; ClassName: ErrorEvent.
 * Author: Lena; Last Modified: 1/4/16 4:59 PM.
 *  This file is originally created by Lena.
 */

package com.jf.orr.net.event;

import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * @Class: BaseEvent
 * @Description: 错误输出封装
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class BaseEvent {

    private String code;
    private String message;
    private Bundle bundle;

    public BaseEvent() {
    }

    public BaseEvent(String code) {
        this.code = code;
    }

    public BaseEvent(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public String toString() {
        return "{code:" + code + " message:" + message + "}";
    }
}
