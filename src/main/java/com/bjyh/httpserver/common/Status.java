package com.bjyh.httpserver.common;

/**
 * @author bjyh
 * @version 1.0.0
 * @description: HTTP/1.1的一些响应状态码，目前只支持其中的部分
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html
 * @date 2022/4/3 17:28
 */
public enum Status {
    _100("100 Continue"),
    _101("101 Switching Protocols"), //
    _200("200 OK"), //
    _201("201 Created"), //
    _301("301 Moved Permanently"), //
    _302("302 Found"), //
    _400("400 Bad Request"), //
    _304("304 Not Modified"), //
    _403("403 Forbidden"), //
    _404("404 Not Found"), //
    _405("405 Method Not Allowed"), //
    _408("408 Request Time-out"), //
    _500("500 Internal Server Error"), //
    _501("501 Not Implemented"), //
    _504("504 Gateway Time-out"), //
    _505("505 HTTP Version not supported"); //

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
