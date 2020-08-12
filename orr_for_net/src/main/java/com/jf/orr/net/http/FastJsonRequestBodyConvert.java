package com.jf.orr.net.http;

import com.alibaba.fastjson.JSON;
import com.jf.orr.log.LogW;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @Class: FastJsonRequestBodyConvert
 * @Description: FastJson 转换器
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class FastJsonRequestBodyConvert<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        String postBody = JSON.toJSONString(value);
        LogW.i("Post Json Body " + postBody);
        return RequestBody.create(postBody.getBytes(StandardCharsets.UTF_8),MEDIA_TYPE);
    }
}
