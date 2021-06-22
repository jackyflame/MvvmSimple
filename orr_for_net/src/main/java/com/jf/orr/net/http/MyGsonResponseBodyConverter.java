package com.jf.orr.net.http;

import com.fawvw.vehice.common.util.LogW;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Discribe: com.vw.ep.framework.http.okhttp
 * @Time: 2020/5/9
 * @Author: Yinhao
 */
public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            String jsonString = new String(value.bytes(), Charset.forName("UTF-8"));
            LogW.i("Parse Response Json :\n" +  jsonString);
            //return adapter.read(jsonReader);
            return adapter.fromJson(jsonString);
        } catch (Exception e) {
            LogW.e("Parse Json Error :" + e.getMessage());
        } finally {
            value.close();
        }
        return null;
    }
}
