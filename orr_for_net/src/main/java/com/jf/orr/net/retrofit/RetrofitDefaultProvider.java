package com.jf.orr.net.retrofit;

import android.text.TextUtils;

import com.jf.orr.net.base.NetHttpConfig;
import com.jf.orr.net.http.FastJsonConvertFactory;
import com.jf.orr.net.http.LogInterceptor;
import com.jf.orr.net.http.TokenInterceptor;
import com.jf.orr.utils.FileControl;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

/**
 * @Class: RetrofitDefaultProvider
 * @Description:
 * @author: github.com/jackyflame
 * @Date: 2021/2/9
 */
public class RetrofitDefaultProvider implements IRetrofitProvider{

    private final String cacheBaseUrl;
    private Retrofit retrofitCache;
    private TokenInterceptor tokenInterceptor;
    private ICreateController createController;

    public RetrofitDefaultProvider(String baseUrl) throws Exception {
        this(baseUrl,new TokenInterceptor());
    }

    public RetrofitDefaultProvider(String baseUrl, TokenInterceptor tokenInterceptor) throws Exception {
        if(TextUtils.isEmpty(baseUrl)){
            throw new Exception("RetrofitDefaultProvider init error: baseUrl is empty!");
        }
        if(tokenInterceptor == null){
            throw new Exception("RetrofitDefaultProvider init error: TokenInterceptor is empty!");
        }
        this.cacheBaseUrl = baseUrl;
        this.tokenInterceptor = tokenInterceptor;
    }

    public String getBaseUrl(){
        return cacheBaseUrl;
    }

    public TokenInterceptor getTokenInterceptor(){
        return tokenInterceptor;
    }

    public void setCreateController(ICreateController controller) {
        this.createController = controller;
    }

    @Override
    public Retrofit createRetrofit(){
        try {
            retrofitCache = new Retrofit
                    .Builder()
                    .baseUrl(getBaseUrl())
                    .client(provideOkHttp(getTokenInterceptor()))
                    .addConverterFactory(FastJsonConvertFactory.create())
                    //.addConverterFactory(MyGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofitCache;
    }

    public OkHttpClient provideOkHttp(TokenInterceptor tokenInterceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(NetHttpConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(NetHttpConfig.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.readTimeout(NetHttpConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.cache(new Cache(new File(FileControl.getInstance()
                .requestCacheFloderPath()), NetHttpConfig.REQUEST_CACHE_SIZE));
        httpClient.connectionPool(new ConnectionPool(
                NetHttpConfig.POOLING_MAX_CONNECTIONS / 2,
                NetHttpConfig.REQUEST_KEEP_ALIVE_DEFAULT / 2, TimeUnit.MILLISECONDS));
        if (tokenInterceptor != null) {
            httpClient.interceptors().add(tokenInterceptor);
        }
        httpClient.interceptors().add(new LogInterceptor());
        return httpClient.build();
    }

    @Override
    public Retrofit provideRetrofit(){
        return provideRetrofit(getTokenInterceptor());
    }

    @Override
    public Retrofit provideRetrofit(TokenInterceptor interceptor){
        try {
            if(isNeedCreate(retrofitCache, interceptor)){
                this.tokenInterceptor = interceptor;
                this.retrofitCache = createRetrofit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofitCache;
    }

    protected boolean isNeedCreate(Retrofit old, TokenInterceptor interceptor){
        if(createController != null){
            return createController.isNeedCreate(old, tokenInterceptor, interceptor);
        }
        if(old == null){
            return true;
        }
        if(!Objects.equals(this.tokenInterceptor,interceptor)){
            return true;
        }
        return false;
    }

    public <T> T create(Class<T> type){
        return provideRetrofit().create(type);
    }

    public <T> T create(Map<String, String> customHeader, Map<String, String> customUrlParam, Class<T> type){
        if(tokenInterceptor == null){
            tokenInterceptor = new TokenInterceptor();
        }
        if(customHeader != null){
            tokenInterceptor.setCustomHeader(customHeader);
        }
        if(customUrlParam != null){
            tokenInterceptor.setCustomUrlParam(customUrlParam);
        }
        return create(tokenInterceptor,type);
    }

    public <T> T create(TokenInterceptor interceptor, Class<T> type){
        try {
            return provideRetrofit(interceptor).create(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ICreateController {
        boolean isNeedCreate(Retrofit old, TokenInterceptor oldToken, TokenInterceptor newToken);
    }
}
