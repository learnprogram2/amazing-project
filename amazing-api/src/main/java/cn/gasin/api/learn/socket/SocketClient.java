package cn.gasin.api.learn.socket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * socket client.
 */
public class SocketClient {


    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    connection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void connection() throws Exception {
        Socket socket = new Socket("localhost", 8080);
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        OutputStream outputStream = socket.getOutputStream();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                // FIXME: 不知道这个关闭顺序是什么.
                inputStream.close();
                inputStreamReader.close();
                socket.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();

        // 1. 发送
        outputStream.write("hello world".getBytes(StandardCharsets.UTF_8)); // 底层拆成TCP包发出去, 还会包装成帧.
        // 2. 读取
        char[] buf = new char[1024 * 1024];
        int len = inputStreamReader.read(buf);

        while (len != -1) {
            String response = new String(buf, 0, len);
            System.out.println("get response:" + response);
            // 3. 一直在这里阻塞读
            len = inputStreamReader.read(buf);
        }

        inputStreamReader.close();
        outputStream.close();
        socket.close();
    }


}
