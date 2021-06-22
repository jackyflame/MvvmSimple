package com.jf.orr.net.retrofit;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.jf.orr.net.base.NetHttpConfig;
import com.jf.orr.net.http.TokenInterceptor;
import com.jf.orr.utils.MD5Util;
import com.jf.orr.log.NetLogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by admin on 2016/10/18.
 */

public class RetrofitHelper {

    private Application context;
    private static List<ReqInterceptor> interceptorList = new ArrayList<>();
    private RetrofitDefaultProvider defaultProvider;
    private WeakHashMap<String,IRetrofitProvider> providerContainer = new WeakHashMap<>();

    private static class SingletonHolder {
        /***单例对象实例*/
        static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitHelper(){
        init(null);
    }

    public void init(Application context){
        init(context, null,null);
    }

    public void init(Application context, String defaultBaseUrl, TokenInterceptor tokenInterceptor){
        try {
            this.context = context;
            if(!TextUtils.isEmpty(defaultBaseUrl)){
                NetHttpConfig.setBaseUrl(defaultBaseUrl);
            }else{
                defaultBaseUrl = NetHttpConfig.getServerBaseUrl();
            }
            if(TextUtils.isEmpty(defaultBaseUrl)){
                NetLogUtil.e("RetrofitHelper init error: defaultBaseUrl is empty");
                throw new Exception("RetrofitHelper init error: defaultBaseUrl is empty");
            }
            if(tokenInterceptor == null){
                defaultProvider = new RetrofitDefaultProvider(defaultBaseUrl);
            }else{
                defaultProvider = new RetrofitDefaultProvider(defaultBaseUrl,tokenInterceptor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static Application getContext() {
        if(getInstance().context == null){
            NetLogUtil.e("context need init in Application onCreate() !!!");
        }
        return getInstance().context;
    }

    public static List<ReqInterceptor> getInterceptorList() {
        return interceptorList;
    }

    public static void cleanInterceptorList(){
        if(interceptorList != null){
            interceptorList.clear();
        }
    }

    public static void addReqInterceptor(ReqInterceptor interceptor){
        if(interceptorList == null){
            interceptorList = new ArrayList<>();
        }
        interceptorList.add(interceptor);
    }

    public static void removeInterceptor(ReqInterceptor interceptor){
        if(interceptorList == null){
            return;
        }
        interceptorList.remove(interceptor);
    }

    public <T> T create(Class<T> type){
        return defaultProvider.create(type);
    }

    public <T> T create(TokenInterceptor interceptor, Class<T> type){
        return defaultProvider.create(interceptor, type);
    }

    public <T> T create(String baseUrl, Class<T> type) {
        if (TextUtils.isEmpty(baseUrl)) {
            return null;
        }
        return getProvider(baseUrl,null).create(type);
    }

    public <T> T create(String baseUrl, TokenInterceptor interceptor, Class<T> type) {
        if (TextUtils.isEmpty(baseUrl)) {
            return null;
        }
        return getProvider(baseUrl,interceptor).create(interceptor, type);
    }

    public <T> T create(IRetrofitProvider provider, Class<T> type) {
        if (provider == null) {
            return null;
        }
        //缓存
        providerContainer.put(getProviderKey(provider.getBaseUrl(),provider.getTokenInterceptor()),provider);
        //创建服务
        return provider.create(type);
    }

    protected IRetrofitProvider getProvider(String baseUrl, TokenInterceptor interceptor){
        String key = getProviderKey(baseUrl,interceptor);
        IRetrofitProvider provider = null;
        if(providerContainer.containsKey(key)){
            provider = providerContainer.get(key);
        }
        if(provider == null){
            try {
                if(interceptor != null){
                    provider = new RetrofitDefaultProvider(baseUrl,interceptor);
                }else{
                    provider = new RetrofitDefaultProvider(baseUrl);
                }
                providerContainer.put(key,provider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return provider;
    }

    protected String getProviderKey(String baseUrl, TokenInterceptor interceptor) {
        try {
            if(!TextUtils.isEmpty(baseUrl)){
                String tail = interceptor != null ? ("_"+interceptor.getUniKey()) : "_0_0";
                return MD5Util.md5_16(baseUrl) + tail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(System.currentTimeMillis());
    }
}
