package com.bjyh.httpserver.common;

/**
 * @author bjyh
 * @version 1.0.0
 * @description: 服务器中文件的类型, 用于响应头部
 * http://en.wikipedia.org/wiki/Internet_media_type
 * @date 2022/4/3 17:36
 */
public enum ContentType {
    HTML("HTML"),
    JPG("JPG"),
    TXT("TXT"),
    ;

    private final String suffix;

    ContentType(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return switch (this) {
            case HTML -> "Content-Type: text/html";
            case TXT -> "Content-Type: text/plain";
            case JPG -> "Content-Type: image/jpeg";
        };
    }
}
