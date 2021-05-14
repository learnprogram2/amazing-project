package cn.gasin.dfs.namenode.editslog;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * 内存的缓冲区: 准备两块缓冲, 交替着来.
 * 1. 为输出到磁盘的缓存
 * <p>
 * FIXME: 这个doubleBuffer, 有很多不好理解的写法, 比如cn.gasin.dfs.namenode.editslog.FSEditLog#syncBuffer()的维护内容, 我觉得有更易读的写法, 需要优化.
 */
@Log4j2
public class DoubleBuffer {
    // 当前的buffer, 接收程序中的editsLog
    private EditLogBuffer currentBuffer;
    // 截取的一块edits log, 没有修改, 用来刷盘, 刷完删掉
    private EditLogBuffer syncBuffer;

    public DoubleBuffer() {
        // 把两块buffer初始化,
        currentBuffer = new EditLogBuffer();
        syncBuffer = new EditLogBuffer();
    }

    public void write(EditLog editLog) throws IOException {
        // 加入到buffer中.
        currentBuffer.offer(editLog);
    }


    /**
     * 交换两个buffer
     * 两个缓冲的交换, 有并发问题, 使用synchronized, 锁当前obj.
     */
    public synchronized void readyToSync() {
        EditLogBuffer temp = currentBuffer;
        currentBuffer = syncBuffer;
        syncBuffer = temp;
    }

    /**
     * 把准备好的一块缓冲区数据刷到磁盘
     * 阻塞方法.
     */
    public void flush() throws IOException {
        syncBuffer.flush();
        log.info("flush success");
    }

    public Long getSyncBufferLatest() {
        return syncBuffer.getLatestLog() == null ? null : syncBuffer.getLatestLog().getTxid();
    }

    /**
     * 是否应该把buffer中的数据刷盘, 就是检查队列中的editLog是不是达到了阈值.
     * FIXME: 这里有一个问题: 如果currentBuf满了但是当前syncBuf还没有刷完, 这个时候要怎么处理? 要阻塞住?
     */
    public boolean shouldSyncToDisk() {
        return currentBuffer.isFull();
    }
}
