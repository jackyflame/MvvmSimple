/*
 * Copyright (c) 2016 ShenBianVip, Ltd.
 * Unauthorized copying of this file, via any medium is strictly prohibited proprietary and
 *  confidential.
 * Created on 3/14/16 3:50 PM
 * ProjectName: shenbian_android_cloud_speaker ; ModuleName: CSpeakerLib ; ClassName: HttpEvent.
 * Author: Lena; Last Modified: 3/14/16 3:50 PM.
 *  This file is originally created by Lena.
 */

package com.jf.orr.net.event;

import android.os.Bundle;
import android.text.TextUtils;

/**
 * @Discribe: HttpEvent
 * @Time: 2020/3/17
 * @Author: github.com/jackyflame
 */
public class HttpEvent {

    private static HttpCodeCastor castor;
    private String extMessage;
    private String code;
    private String message;
    private Bundle bundle;

    public HttpEvent() {}

    public HttpEvent(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        if(castor != null){
            String message = castor.getMessage(getCode());
            if(TextUtils.isEmpty(message)){
                return this.message;
            }
            return message;
        }
        return this.message;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public String getExtMessage() {
        return extMessage;
    }

    public static void setCastor(HttpCodeCastor castor) {
        HttpEvent.castor = castor;
    }

    public interface HttpCodeCastor{
        String getMessage(String code);
    }
}
