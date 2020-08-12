package com.jf.orr.net.retrofit;

import com.jf.orr.net.NetConst;
import com.jf.orr.net.http.FastJsonConvertFactory;
import com.jf.orr.net.http.OkHttpHelper;
import com.jf.orr.net.http.TokenInterceptor;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * @Class: RetrofitHelper
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class RetrofitHelper {

    private Retrofit retrofitCache;
    private String customBaseUrl;

    private static class SingletonHolder {
        /***单例对象实例*/
        static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Retrofit provideRetrofit() {
        return provideRetrofit(NetConst.getServerBaseUrl());
    }

    private Retrofit provideRetrofit(String baseUrl) {
        return provideRetrofit(baseUrl,null);
    }

    private Retrofit provideRetrofit(Map<String, String> customHeader) {
        return provideRetrofit(NetConst.getServerBaseUrl(), customHeader,false);
    }

    private Retrofit provideRetrofit(Map<String, String> customHeader, boolean createForce) {
        return provideRetrofit(NetConst.getServerBaseUrl(),customHeader, createForce);
    }

    private Retrofit provideRetrofit(String baseUrl, Map<String, String> customHeader) {
        if (retrofitCache == null || !baseUrl.equals(customBaseUrl)) {
            customBaseUrl = baseUrl;
            retrofitCache = provideRetrofit(customBaseUrl, customHeader,true);
        }
        return retrofitCache;
    }

    private Retrofit provideRetrofit(String baseUrl, Map<String, String> customHeader, boolean createForce) {
        if (retrofitCache == null || !baseUrl.equals(customBaseUrl) || createForce) {
            TokenInterceptor interceptor = new TokenInterceptor();
            interceptor.setCustomHeader(customHeader);
            retrofitCache =  provideNewRetrofit(interceptor,baseUrl);
        }
        return retrofitCache;
    }

    private Retrofit provideNewRetrofit(TokenInterceptor interceptor, String baseUrl) {
        customBaseUrl = baseUrl;
        retrofitCache = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(OkHttpHelper.getInstance().provideOkHttp(interceptor))
                .addConverterFactory(FastJsonConvertFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return retrofitCache;
    }

    public <T> T defaultCall(Class<T> type) {
        return provideRetrofit().create(type);
    }

    public <T> T callWithCustom(String baseUrl, Class<T> type) {
        if (baseUrl == null || baseUrl.isEmpty()) {
            return null;
        }
        return provideRetrofit(baseUrl).create(type);
    }

    public <T> T callWithHeader(String baseUrl, Map<String, String> customHeader, Class<T> type) {
        if (baseUrl == null || baseUrl.isEmpty()) {
            return null;
        }
        return provideRetrofit(baseUrl, customHeader).create(type);
    }

    public <T> T callWithHeader(Map<String, String> customHeader, Class<T> type) {
        return provideRetrofit(customHeader).create(type);
    }

    public <T> T callWithHeader(Map<String, String> customHeader, boolean createForce, Class<T> type) {
        return provideRetrofit(customHeader, createForce).create(type);
    }
}
