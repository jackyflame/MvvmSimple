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

/**
 * @Class: HttpEvent
 * @Description: 错误输出封装
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class HttpEvent extends BaseEvent {

    public HttpEvent() {
    }

    public HttpEvent(String code, String message) {
        super(code, message);
    }
}
