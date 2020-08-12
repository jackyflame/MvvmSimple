package com.jf.orr.net.http;

import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * @Class: OkHttpHelper
 * @Description: OkHttp Client 帮助类
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class OkHttpHelper {

    private OkHttpHelper(){}

    private static class SingletonHolder{
        public static OkHttpHelper instance = new OkHttpHelper();
    }

    public static OkHttpHelper getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * OkHttpClient 日志记录实例
     */
    public OkHttpClient provideOkHttp() {
        return provideOkHttp(new TokenInterceptor());
    }

    /**
     * OkHttpClient 日志记录实例
     */
    public OkHttpClient provideOkHttp(TokenInterceptor interceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(OkHttpConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(OkHttpConfig.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.readTimeout(OkHttpConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.cache(new Cache(new File(Environment.getExternalStorageDirectory()
                .getPath()+OkHttpConfig.CACHE_FILE), OkHttpConfig.REQUEST_CACHE_SIZE));
        httpClient.connectionPool(new ConnectionPool(
                OkHttpConfig.POOLING_MAX_CONNECTIONS / 2,
                OkHttpConfig.REQUEST_KEEP_ALIVE_DEFAULT / 2, TimeUnit.MILLISECONDS));
        //添加header
        if(interceptor != null){
            httpClient.interceptors().add(interceptor);
        }
        httpClient.interceptors().add(new LogInterceptor());
        return httpClient.build();
    }

}
