package cn.gasin.api.learn.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("I:\\work\\stone_fir\\阶段五-复杂业务场景下的系统设计\\4. NIO\\001~022资料\\011_从磁盘文件中读取数据到Buffer缓冲区里\\ff.txt", "rw");
        FileChannel channel = file.getChannel();
        // 上一个锁. 共享锁或者独占锁.
        channel.lock(0, Integer.MAX_VALUE, true);


        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
        channel.read(byteBuffer);
        byte[] data = new byte[8];
        byteBuffer.flip(); // 写完想读, 把position = 0，limit = 原来的position
        byteBuffer.get(data);
        System.out.println(new String(data));

        channel.close();
        file.close();
    }

    // 11. channel从文件中读取数据
    public void channelReadFromFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("I:\\work\\stone_fir\\阶段五-复杂业务场景下的系统设计\\4. NIO\\001~022资料\\011_从磁盘文件中读取数据到Buffer缓冲区里\\ff.txt");
        FileChannel channel = fileInputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
        channel.read(byteBuffer);
        byte[] data = new byte[8];
        byteBuffer.flip(); // 写完想读, 把position = 0，limit = 原来的position
        byteBuffer.get(data);
        System.out.println(new String(data));

        channel.close();
        fileInputStream.close();
    }

    // 9. 选择channel的位置: select position in channel
    public void selectPositionInChannel() throws IOException {
        // ======================== 选择位置写入
        // 其次如果你要基于FileChannel随机写，可以调整FileChannel的position
        FileOutputStream fileOutputStream = new FileOutputStream(".\\xx.txt");
        FileChannel channel = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{52, 2, 13, 42, 43});
        channel.write(byteBuffer);

        byteBuffer.rewind();
        // channel也是有index的概念的
        channel.position(5);
        channel.write(byteBuffer);

        channel.force(true);

        channel.close();
        fileOutputStream.close();
    }


    // 8. 基本使用: 把buffer通过channel输出到文件.
    public static void basicUse() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\yw31830\\IdeaProjects\\amazing-project\\amazing-filesystem\\dfs-namenode\\target\\classes\\editLogs\\xx.txt");
        FileChannel channel = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{52, 2, 13, 42, 43});
        channel.write(byteBuffer);
        channel.close();
        fileOutputStream.close();
    }


}
