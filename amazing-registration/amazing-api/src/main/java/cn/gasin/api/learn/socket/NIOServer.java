package cn.gasin.api.learn.socket;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractSelector;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 使用NIO的多路复用模式来改造socket直连
 * <p>
 * socket -> inputStream -> Buffer -> Channel -> selector -> Executor
 */
public class NIOServer {
    public static CharsetEncoder ENCODER = StandardCharsets.UTF_8.newEncoder();
    public static CharsetDecoder DECODER = StandardCharsets.UTF_8.newDecoder();

    public static void main(String[] args) throws IOException {
        new NIOServer().init().listen();
    }

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private Selector selector;
//    ExecutorService executorService;
//    // 通过queue来传递key, 不能和直接拿到一个key传给线程池, 会有key操作锁不上的问题. WARN: 不能用队列会有并发问题, 具体不知道怎么回事.可能是TCP连接请求的重发?, 我感觉
//    ArrayBlockingQueue<SelectionKey> queue = new ArrayBlockingQueue<>(100);

    @SneakyThrows
    public NIOServer init() {
        selector = Selector.open();

        // 1. 创建一个ServerSocketChannel+ServerSocket, 绑定到8080端口.
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //  要设定为非阻塞的, 因为他里面注册的操作有一个锁对象用锁实现的, 所以要阻塞.
        serverSocketChannel.configureBlocking(false);// 阻塞调用channel的connect(), read()和 write()了
        // 这个是创建一个ServerSocketAdaptor, 注释里写了, 让ServerSocketChannel看起来像是一个socket
        // serverSocketChannel.socket().bind(new InetSocketAddress(8080), 100);
        serverSocketChannel.bind(new InetSocketAddress(8080)); // 这个和上个都一样, 不知道为什么.



        // 2. 绑定到selector上, 开始等待处理
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
                    String req = DECODER.decode(readBuffer).toString();
                    System.out.println(req);
                    socketChannel.write(ENCODER.encode(CharBuffer.wrap("thanks, I get you request")));
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

}
