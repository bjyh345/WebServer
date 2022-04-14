package com.bjyh.httpserver.common;

/**
 * @author bjyh
 * @version 1.0.0
 * @description: 服务器支持的请求方法，目前仅支持GET
 * @date 2022/4/4 9:32
 */
public enum Method {
    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    UNRECOGNIZED(null),
    ;
    private final String method;

    Method(String method) {
        this.method = method;
    }
}
