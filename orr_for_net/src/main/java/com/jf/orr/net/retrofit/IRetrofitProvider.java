package com.jf.orr.net.retrofit;

import com.jf.orr.net.http.TokenInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @Class: IRetrofitCreator
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/2/9
 */
public interface IRetrofitProvider {

    String getBaseUrl();

    TokenInterceptor getTokenInterceptor();

    OkHttpClient provideOkHttp(TokenInterceptor tokenInterceptor);

    Retrofit createRetrofit();

    Retrofit provideRetrofit();

    Retrofit provideRetrofit(TokenInterceptor interceptor);

    <T> T create(Class<T> type);

    <T> T create(TokenInterceptor interceptor, Class<T> type);

}
