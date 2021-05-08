package cn.gasin.api.learn.socket;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用NIO的多路复用模式来改造socket直连
 * <p>
 * socket -> inputStream -> Buffer -> Channel -> selector -> Executor
 */
public class NIOServer {
    private static CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
    private static CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

    public static void main(String[] args) throws IOException {
        new NIOServer().init().listen();
    }

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private Selector selector;
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    @SneakyThrows
    public NIOServer init() {
        // 创建一个ServerSocketChannel+ServerSocket, 绑定到8080端口.
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080), 100);

        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        return this;
    }

    public void listen() throws IOException {
        while (true) {
            // * 这一步是select开始检查哪个channel准备好了.
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handleKey(key);
            }
        }
    }

    private void handleKey(SelectionKey key) throws IOException {

        executorService.execute(
                () -> {
                    try {
                        if (key.isAcceptable()) {
                            // 连接请求: OP_ACCEPT的key
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept(); // TODO 这个accept是创建一个channel么?
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                        } else if (key.isReadable()) {
                            // 消息就绪了
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            readBuffer.clear();
                            int len; // FIXME: 这里如果对方断连, 不会减selector里面就绪的channel, 会造成死循环.
                            while ((len = socketChannel.read(readBuffer)) > 0) {
                                readBuffer.flip();
                                String req = decoder.decode(readBuffer).toString();
                                System.out.println(req);
                                socketChannel.write(encoder.encode(CharBuffer.wrap("thanks, I get you request")));
                            }
                            // 着处理完连接, 就把这个socket给关掉了!!!!!!!!!
                            // socketChannel.close();
                        }
                    } catch (Exception e) {
                        try {
                            key.channel().close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
        );


    }

}
