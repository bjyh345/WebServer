package com.bjyh.httpserver.http;

import com.bjyh.httpserver.common.Constants;
import com.bjyh.httpserver.common.ContentType;
import com.bjyh.httpserver.common.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bjyh
 * @version 1.0.0
 * @description: 处理HTTP响应消息并发送
 * @date 2022/4/4 21:54
 */
public class HttpResponse {
    private static Logger log = LogManager.getLogger(HttpResponse.class);


    private List<String> headers = new ArrayList<>();

    // 响应主体
    private byte[] body;

    // 根据HttpRequest对象来确定要响应什么信息，即初始HttpResponse对象
    public HttpResponse(HttpRequest httpRequest) {
        switch (httpRequest.getMethod()) {
            case GET -> {
                String uri = httpRequest.getUri();
                Path path = Path.of(Constants.STATIC_ROOT_PATH + uri);
                try {
                    // 如果请求的是个目录，则发送目录文件夹的所有文件
                    if (Files.isDirectory(path)) {
                        setHeaders(Status._200);
                        addHeader(ContentType.HTML.toString());
                        StringBuffer res = new StringBuffer("<html><head><title>");
                        res.append(uri)
                                .append("</title></head><body><h1> Get the all files from: ")
                                .append(uri)
                                .append("</h1><hr>");

                        Files.list(path).forEach(p -> res.append("<a href=\"")
                                .append(p.toUri())
                                .append("\">")
                                .append(p.getFileName())
                                .append("</a>\n"));
                        res.append("</body></html>");
                        setBody(res.toString());
                        return;
                    }

                    // 如果请求的是文件，且存在这个文件，则发送这个文件
                    if (Files.exists(path)) {
                        setHeaders(Status._200);
                        String type = path.toString();
                        type = type.substring(type.indexOf('.') + 1).toUpperCase();
                        try {
                            addHeader(ContentType.valueOf(type).toString());
                        } catch (IllegalArgumentException e) {
                            log.error("Cannot get file of this type.", e);
                        }
                        setBody(Files.readAllBytes(path));
                        return;
                    }

                    // 不存在请求的文件或目录
                    setHeaders(Status._404);
                    setBody(Status._404.toString());
                    log.warn("File not found: " + httpRequest.getUri());

                } catch (IOException e) {
                    setHeaders(Status._400);
                    setBody(Status._400.toString());
                    log.error("Response Error", e);
                }
            }
            // 未能识别的方法
            case UNRECOGNIZED -> {
                setHeaders(Status._400);
                setBody(Status._400.toString());
            }
            // 能识别的但未实现的方法
            default -> {
                setHeaders(Status._501);
                setBody(Status._501.toString());
            }
        }
    }

    private void setBody(String body) {
        this.body = body.getBytes();
    }

    private void setBody(byte[] body) {
        this.body = body;
    }

    public void addHeader(String header) {
        headers.add(header);
    }

    // 设置响应行(第一行)version + status, 以及固定头部
    private void setHeaders(Status status) {
        addHeader(Constants.HTTP_VERSION + " " + status.toString());
        addHeader("Connection: Keep-Alive");
        addHeader("Date: " + new Date());
        addHeader("Server: bjyh.httpServer");
    }

    // 发送响应信息：响应头+响应主体
    public void sendResponse(OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);

        for (String head : headers) {
            dos.writeBytes(head + "\r\n");
        }
        dos.writeBytes("\r\n");

        if (body != null) {
            dos.write(body);
        }
        dos.flush();
    }
}
