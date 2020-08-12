package com.jf.orr.net.http;

/**
 * @Class: OkHttpConfig
 * @Description: OkHttp配置文件
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public interface OkHttpConfig {

    long CONNECT_TIMEOUT = 5_000;
    long WRITE_TIMEOUT = 5_000;
    long READ_TIMEOUT = 5_000;
    int REQUEST_CACHE_SIZE = 10 * 1024 * 1024;
    int POOLING_MAX_CONNECTIONS = 5;
    int REQUEST_KEEP_ALIVE_DEFAULT = 30000;

    String CACHE_FILE = "/com.jf.orr/httpCache";

}
