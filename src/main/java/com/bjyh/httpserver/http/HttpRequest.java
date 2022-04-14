package com.bjyh.httpserver.http;

import com.bjyh.httpserver.common.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bjyh
 * @version 1.0.0
 * @description: 解析HTTP请求信息，包括方法，URI，version, header
 * @date 2022/4/4 21:48
 */
public class HttpRequest {
    private static Logger log = LogManager.getLogger(HttpRequest.class);

    private Method method;

    private String uri;

    private String httpVersion;

    private List<String> headers = new ArrayList<>();

    // 根据输入流初始化HttpRequest对象
    public HttpRequest(BufferedReader br) {
        try {
            String line = br.readLine();
            if (line != null) {
                parseRequestLine(line);
            }
            String s;
            while (!(s = br.readLine()).equals("")) {
                addHeader(s);
            }
        } catch (IOException e) {
            log.error("Failed to read request line", e);
        }
    }

    // 解析请求信息的第一行(请求行)
    public void parseRequestLine(String line) {
        log.info(line);
        String[] lines = line.split(" ");
        try {
            method = Method.valueOf(lines[0]);
        } catch (IllegalArgumentException e) {
            method = Method.UNRECOGNIZED;
            log.error("Can't recognize this method.", e);
        }
        uri = decode(lines[1]);
        httpVersion = lines[2];
        log.info("URI: " + uri);
    }

    // URL decoding
    private String decode(String uri) {
        return URLDecoder.decode(uri, StandardCharsets.UTF_8);
    }

    // TODO：解析请求URI的参数
    public void parseQueryParameters(String query) {

    }

    public void addHeader(String header) {
        log.info(header);
        headers.add(header);
    }

    public String getUri() {
        return uri;
    }

    public Method getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
