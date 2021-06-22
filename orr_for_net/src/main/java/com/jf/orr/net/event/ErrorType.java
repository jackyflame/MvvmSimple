package com.jf.orr.net.event;

/**
 * @Discribe: com.vw.base.constants
 * @Time: 2020/3/17
 * @Author: Yinhao
 */
public interface ErrorType {

    String ERROR_INVALID_USER = "401";
    String ERROR_AUTH = "403";
    String ERROR_SERVER_AUTH = "1001";
    String ERROR_SERVER = "1003";

    String ERROR_REQ_ERROR = "500";

    String ERROR_NETWORK = "1004";
    String ERROR_NETWORK_MSG = "无数据网络，请开关WiFi或者移动网络";
    String ERROR_NETWORK_SERVER = "1005";
    String ERROR_NETWORK_SERVER_MSG = "系统繁忙，请重试";
    String ERROR_NETWORK_LOCAL = "1006";

    String ERROR_RESPONSE_NULL = "9988";
    String ERROR_OTHER = "9999";

}
