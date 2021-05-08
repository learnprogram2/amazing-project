package cn.gasin.api.learn.socket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        // 1. 接收连接请求
        Socket acceptSocket = serverSocket.accept();
        // 2. 拿到input和output的Stream
        InputStream inputStream = acceptSocket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        OutputStream outputStream = acceptSocket.getOutputStream();


        new Thread(() -> {
            try {
                Thread.sleep(5000);
                // FIXME: 不知道这个关闭顺序是什么.
                acceptSocket.close();
                serverSocket.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();


        // 3. 读取数据
        char[] buf = new char[1024 * 1024];
        int len = -1;
        while ((len = inputStreamReader.read(buf)) > 0) {
            String requestStr = new String(buf, 0, len);
            System.out.println("get request from client:" + requestStr);
            outputStream.write("thanks for sending me".getBytes(StandardCharsets.UTF_8));
        }

        OutputStream out = acceptSocket.getOutputStream();
        out.write("收到你的消息了".getBytes());


        inputStreamReader.close();
        outputStream.close();
        acceptSocket.close();
        serverSocket.close();
    }
}
