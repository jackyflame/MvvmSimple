package com.jf.orr.net.base;

public class NetHttpConfig {

    public static final long CONNECT_TIMEOUT = 5_000;
    public static final long WRITE_TIMEOUT = 5_000;
    public static final long READ_TIMEOUT = 5_000;
    public static final int REQUEST_CACHE_SIZE = 10 * 1024 * 1024;
    public static final int POOLING_MAX_CONNECTIONS = 5;
    public static final int REQUEST_KEEP_ALIVE_DEFAULT = 30000;

    private static String BaseUrl = "";

    public static String getServerBaseUrl() {
        return BaseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        BaseUrl = baseUrl;
    }
}
