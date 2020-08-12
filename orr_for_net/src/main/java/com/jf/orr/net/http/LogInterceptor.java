package com.jf.orr.net.http;

import com.jf.orr.log.LogW;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @Class: TokenInterceptor
 * @Description: 封装网络请求日志
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class LogInterceptor implements Interceptor {

    private final Logger sLogger = Logger.getLogger(LogW.TAG);

    public LogInterceptor() {
        sLogger.setLevel(Level.FINE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();
        if (LogW.isDebug) {
            sLogger.info(String.format("Sending request %s on Connection: %s %n Headers: %s ",
                    request.url(),
                    chain.connection(),
                    request.headers()));
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        if (LogW.isDebug) {
            sLogger.info(String.format(Locale.CHINA,
                    "Received response for " +
                            "%s in %.1fms %n" +
                            "Response Status Code: %s %n" +
                            "Response Headers: %s %n" +
                            "Response CacheControl:%s %n" +
                            "Response Body :%s %n" +
                            "Is From Cached Response: %s %n",
                    request.url(),
                    (t2 - t1) / 1e6d,
                    response.code(),
                    response.headers(),
                    response.cacheControl(),
                    response,
                    response.cacheResponse() == response.networkResponse()));
            if(response.code() != 200){
                ResponseBody responseBody = response.body();
                if(responseBody != null) {
                    Charset UTF8 = StandardCharsets.UTF_8;
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.getBuffer();

                    Charset charset = UTF8;
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        try {
                            charset = contentType.charset(UTF8);
                        } catch (UnsupportedCharsetException e) {
                            e.printStackTrace();
                        }
                    }
                    String respBody = buffer.clone().readString(charset);
                    sLogger.info(String.format(" >>> Response Body Content :%s %n",respBody ));
                }

            }
        }
        return response;
    }
}
