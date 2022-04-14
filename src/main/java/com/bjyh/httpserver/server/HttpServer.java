package com.bjyh.httpserver.server;

import com.bjyh.httpserver.common.Constants;
import com.bjyh.httpserver.handler.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bjyh
 * @version 1.0.0
 * @description: 启动web服务器，监听端口9090
 * @date 2022/4/13 11:38
 */
public class HttpServer {

    private static Logger log = LogManager.getLogger(HttpServer.class);

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.PORT);
        System.out.println("Http server listening on port " + Constants.PORT);
        Socket socket;
        while ((socket = serverSocket.accept()) != null) {
            System.out.println("Received connection from " +
                    socket.getInetAddress().toString());
            HttpHandler handler = new HttpHandler(socket);
            Thread t = new Thread(handler);
            t.start();
        }

    }

    public static void main(String[] args) {
        try {
            HttpServer server = new HttpServer();
            server.start();
        } catch (IOException e) {
            log.error("Start Error", e);
        }
    }
}
