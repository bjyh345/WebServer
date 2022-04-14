package com.bjyh.httpserver.handler;

import com.bjyh.httpserver.http.HttpRequest;
import com.bjyh.httpserver.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author bjyh
 * @version 1.0.0
 * @description: 处理http请求的线程类
 * @date 2022/4/13 11:31
 */
public class HttpHandler implements Runnable {

    private static Logger log = LogManager.getLogger(HttpHandler.class);

    private Socket socket;

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in;
        OutputStream out;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = socket.getOutputStream();

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(request);
            response.sendResponse(out);
            socket.close();

        } catch (IOException e) {
            log.error("Runtime error", e);
        }
    }
}
