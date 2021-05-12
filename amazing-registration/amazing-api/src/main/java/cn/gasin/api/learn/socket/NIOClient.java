package cn.gasin.api.learn.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static cn.gasin.api.learn.socket.NIOServer.DECODER;
import static cn.gasin.api.learn.socket.NIOServer.ENCODER;

/**
 * NIO 实现 client
 */
public class NIOClient {

    // private static Selector selector = null;

    private static Executor executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {


        for (int i = 0; i < 1; i++) {
            Selector selector = Selector.open();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 1. 创建一个socket->channel, 连接上去.
                        SocketChannel socketChannel = SocketChannel.open();
                        socketChannel.configureBlocking(false);
                        socketChannel.connect(new InetSocketAddress("localhost", 8080));

                        // 2. 把channel注册到selector上
                        socketChannel.register(selector, SelectionKey.OP_CONNECT);

                        // 3. 开始处理连接相应
                        while (true) {
                            selector.select();
                            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                            while (iterator.hasNext()) {
                                SelectionKey key = iterator.next();
                                iterator.remove();
                                if (key.isConnectable()) {
                                    // 1. server响应了连接成功, 赶紧把socket置于连接状态就行了.
                                    SocketChannel channel = (SocketChannel) key.channel();
                                    if (channel.isConnectionPending()) {
                                        // 这个channel已经连上了
                                        boolean connectSucc = channel.finishConnect();
                                        if (!connectSucc) {
                                            // 没连上, 放弃吧.
                                            key.cancel();
                                        }
                                        // 开始建立正常的读取事件.
                                        key.interestOps(SelectionKey.OP_READ);
                                        // 开始发送消息什么的就好了
                                        ByteBuffer byteBuffer = ENCODER.encode(CharBuffer.wrap("我是client")); // FIXME: 这个encoder有并发问题.
                                        channel.write(
                                                byteBuffer
                                        );
                                    }
                                } else if (key.isReadable()) {
                                    // socket接收到了server的信息, 写到buffer里面
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    SocketChannel channel = (SocketChannel) key.channel();
                                    channel.read(byteBuffer);
                                    // 2. buffer读出来
                                    byteBuffer.flip();
                                    String response = DECODER.decode(byteBuffer).toString();
                                    System.out.println("受到相应:" + response);
                                    // 3. 再给他返回去
                                    //  channel.write(ENCODER.encode(CharBuffer.wrap("xixixixi, 在请求一次")));
                                } else if (key.isWritable()) {
                                    System.out.println("为什么是write????????????????////");
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
}
