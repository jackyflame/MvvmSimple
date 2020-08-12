package com.jf.orr.net.http;
import com.alibaba.fastjson.JSON;
import com.jf.orr.log.LogW;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Class: FastJsonResponseBodyConverter
 * @Description: FastJson 转换器
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String jsonString = new String(value.bytes(), StandardCharsets.UTF_8);
            LogW.i("Parse Response Json :\n" + jsonString);
            return JSON.parseObject(jsonString, type);
        } catch (Exception e) {
            LogW.e("Parse Json Error :" + e.getMessage());
        }
        return null;
    }
}
