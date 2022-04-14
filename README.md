# WebServer
A very simple web server.

描述：一个非常简单的web服务器，仅支持客户端向服务器发送HTTP请求获取服务器上的文件，写该项目目的是了解web服务器的原理。

1. 根据Socket编程，服务器与客户端建立TCP连接后，手动解析HTTP请求报文信息，再根据请求信息生成响应报文并返回给客户端，**目前仅支持GET请求获取服务器文件**；
2. 使用log4j2框架来记录日志，将日志输出至控制台；


TODO:
1. 增加其他方法如POST, HEAD的支持；
2. 使用Velocity支持动态资源的请求；

项目框架：

<img src="https://raw.githubusercontent.com/bjyh345/imgBeg/master/image-20220414180958536.png" alt="image-20220414180958536" style="zoom:67%;" />
