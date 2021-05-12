package cn.gasin.api.learn.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 通过byteArr和四个指针实现buffer
 */
public class BufferDemo {
    // capacity, limit, position, mark.

    // 缓冲区大小
    int capacity = 10;
    char[] arr = new char[capacity];
    // 缓冲区使用限制,
    int limit = 10;
    int position = 0;
    int park = 0;

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        // 重新用byteBuffer
        byteBuffer.clear();
        // 写完想读取: position放到起始点, limit放到position, 丢弃mark.
        byteBuffer.flip();
        // 想从头读一遍: position放到起点.
        byteBuffer.rewind();

    }
    // 6. bufferAPI: 使用buffer/复用buffer
    public void bufferClearFlipRewind() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        // 重新用byteBuffer
        byteBuffer.clear();
        // 写完想读取: position放到起始点, limit放到position, 丢弃mark.
        byteBuffer.flip();
        // 想从头读一遍: position放到起点.
        byteBuffer.rewind();
    }

    // 5. buffer的读写
    public void bufferOperate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        // 拿到position处的byte, position++
        byte b = byteBuffer.get();
        // 把byte设置到position处, position++
        byteBuffer.put("c".getBytes(StandardCharsets.UTF_8));
    }

    // 4. directory模式的buffer和heap模式的buffer有什么不同
    public void directModeBuffer() {
        // 创建一个heap模式的buffer: 线程 --> [heap Java 数组] --> [native 内存 数组] -->
        ByteBuffer allocate = ByteBuffer.allocate(5);

        // 创建一个directory模式的buffer: 线程 --> [native 内存 数组] -->
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(5);
    }

    // 3. 学习简单API: 简单 把byte数组包装一个byteBuffer（heapByteBuffer)
    public void swapAByteBuffer() {
        // 把byte[]包装成一个ByteBuffer了, wrap是包装成一个HeapByteBuffer. 然后初始化几个参数.
        // buffer范围就是[0,10)
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[10]);

        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.mark());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.remaining());
    }

}
