package com.jf.orr.net.http;

import android.net.Uri;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
    private Map<String,String> customUrlParam = new HashMap<>();

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

    public void setCustomUrlParam(Map<String,String> customUrlParam){
        if(customUrlParam == null){
            this.customUrlParam.clear();
        }else{
            this.customUrlParam = customUrlParam;
        }
    }

    public void addCustomUrlParam(String key,String value){
        customUrlParam.put(key,value);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        String url = request.url().toString();
        Uri uri = Uri.parse(url);
        Set<String> querySet = uri.getQueryParameterNames();
        Iterator<String> queryIterator = querySet.iterator();
        Map<String, String> queryMap = new HashMap<>();
        while (queryIterator.hasNext()) {
            String key = queryIterator.next();
            queryMap.put(key, uri.getQueryParameter(key));
        }
        //追加自定url-param
        queryMap.putAll(customUrlParam);

        Request.Builder builder = chain.request().newBuilder();
        //组装URL
        StringBuilder sbUrl = new StringBuilder();
        if (!uri.toString().contains("?")) {
            sbUrl.append(url);
        } else {
            sbUrl.append(url.substring(0, url.indexOf("?")));
        }
        if(queryMap.size() > 0){
            sbUrl.append("?");
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                if (!sbUrl.toString().endsWith("?")) {
                    sbUrl.append("&");
                }
                sbUrl.append(entry.getKey());
                sbUrl.append("=");
                sbUrl.append(entry.getValue());
            }
            builder.url(sbUrl.toString());
        }
        builder.header("Content-Type", "application/json")
                .addHeader("Accept", "application/json");

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenInterceptor that = (TokenInterceptor) o;
        // Objects.equals(customHeader, that.customHeader) &&
        //        Objects.equals(customUrlParam, that.customUrlParam);
        return isTokenSame(that);
    }

    protected boolean isTokenSame( TokenInterceptor other){
        return isMapEquals(customHeader,other.customHeader)
                && isMapEquals(customUrlParam,other.customUrlParam);
    }

    protected boolean isMapEquals(Map<String,String> map1, Map<String,String> map2){
        if(map1 == null && map2 == null){
            return true;
        }else if(map1 == null){
            return false;
        }else if(map2 == null){
            return false;
        }else if(map1.isEmpty() && map2.isEmpty()){
            return true;
        }else if(map1.size() != map2.size()){
            return false;
        }
        for (Map.Entry<String,String> entry1: map1.entrySet()) {
            String m1value = entry1.getValue() == null?"":entry1.getValue();
            String m2value = map2.get(entry1.getKey())==null?"":map2.get(entry1.getKey());
            if (!m1value.equals(m2value)) {
                return false;
            }
        }
        return true;
    }

    public String getUniKey(){
        return customHeader.size()+"_"+customUrlParam.size();
    }

}
