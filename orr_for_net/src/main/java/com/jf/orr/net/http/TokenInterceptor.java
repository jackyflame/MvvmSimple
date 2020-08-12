package com.jf.orr.net.http;

import com.jf.orr.net.NetConst;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Class: TokenInterceptor
 * @Description: 封装网络请求header和token
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class TokenInterceptor implements Interceptor {

    private Map<String,String> customHeader = new HashMap<>();

    public void addHeader(String key,String value){
        customHeader.put(key,value);
    }

    public void setCustomHeader(Map<String,String> customHeader){
        if(customHeader == null){
            this.customHeader.clear();
        }else{
            this.customHeader = customHeader;
        }
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader(NetConst.KEY_APPKEY, NetConst.MY_APP_KEY);

        if(customHeader.size() > 0){
            Iterator<Map.Entry<String, String>> entries = customHeader.entrySet().iterator();
            while(entries.hasNext()){
                Map.Entry<String, String> entry = entries.next();
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addHeader(key, value);
            }
        }

        return chain.proceed(builder.build());
    }
}
