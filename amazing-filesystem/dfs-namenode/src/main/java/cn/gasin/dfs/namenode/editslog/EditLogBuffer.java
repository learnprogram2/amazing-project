package cn.gasin.dfs.namenode.editslog;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import static cn.gasin.dfs.namenode.config.Config.EDIT_LOG_BUFFER_SIZE_SYNC_THRESHOLD;

/**
 * 单个buffer
 */
@Log4j2
public class EditLogBuffer {
    // 字节数组buffer, 通过fileChannel+buffer刷出去
    private ByteArrayOutputStream bytesBuffer;
    private ByteBuffer nioBuffer;
    /**
     * pointer: 最后一条log, note: 暂时不用volatile/控制并发, 因为buffer上上层的FSEditLog控制了并发, 不会有并发问题.
     */
    @Getter
    private EditLog latestLog;
    /** pointer: 第一条log */
    @Getter
    private EditLog firstLog;
    @Getter
    private EditLog latestLogSynced;

    public EditLogBuffer() {
        // 这里来个两倍的buffer, 其实没太大用, 因为在isFull就已经判断了.
        bytesBuffer = new ByteArrayOutputStream(EDIT_LOG_BUFFER_SIZE_SYNC_THRESHOLD * 2);
        nioBuffer = ByteBuffer.allocate(EDIT_LOG_BUFFER_SIZE_SYNC_THRESHOLD * 2);
    }

    /**
     * 添加到缓冲区一条数据
     */
    public void offer(EditLog editLog) throws IOException {
        firstLog = firstLog == null ? editLog : firstLog;
        latestLog = editLog; //  有没有并发乱序的情况: 从顶层控制, 比较简单直接.
        bytesBuffer.write(editLog.getBytes());
        bytesBuffer.write("\n".getBytes(StandardCharsets.UTF_8));
        log.info("editLogBuffer cache the editLog, txId:{}", editLog.getTxid());
    }

    public boolean isFull() {
        return bytesBuffer.size() > EDIT_LOG_BUFFER_SIZE_SYNC_THRESHOLD;
    }


    /**
     * 把缓冲区的数据刷到磁盘
     * TODO: 这个要, 怎么保证强制刷新成功啊
     */
    public void flush() throws IOException {
        if (bytesBuffer.size() == 0 || firstLog == null || latestLog == null) return;

        // 这里可以重用, 但是写完之后要flip重置一下position指针, 不然channel读不到!!
        nioBuffer.clear();
        nioBuffer.put(bytesBuffer.toByteArray());
        nioBuffer.flip();
        try (
                RandomAccessFile file = new RandomAccessFile(generateLogFileName(), "rw"); // 读写模式，数据写入缓冲区中
                FileChannel channel = file.getChannel();
        ) {
            channel.write(nioBuffer);
            channel.force(true);
        }
        // 我操, 我写的这个方法太漂亮了.
        reset();
    }

    @SneakyThrows
    private String generateLogFileName() {
        String path = EditLogBuffer.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/editLogs/";
        File temp = new File(path);
        temp.mkdirs();
        return path + firstLog.getTxid() + "-" + latestLog.getTxid() + ".log";
    }

    /**
     * 当前缓冲区大小
     */
    public int size() {
        return bytesBuffer.size();
    }

    /**
     * 清空缓存
     */
    private void reset() {
        // 1. 先把字节数组重设
        bytesBuffer.reset();
        // 2. 维护的指针重设
        latestLogSynced = latestLog == null ? latestLogSynced : latestLog;
        latestLog = null;
        firstLog = null;
    }

}
