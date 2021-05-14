package cn.gasin.dfs.namenode.editslog;

import lombok.extern.log4j.Log4j2;

/**
 * maintain edit-log.
 * 专门负责管理写入edits log到磁盘文件里去
 * <p>
 * 51_案例实战：基于synchronized实现edits log的分段加锁机制
 * 52_案例实战：基于wait与notify实现edits log批量刷磁盘
 * <p>
 * FSEditLog maintains a log of the namespace modifications
 * FSEditLog管理整个namespace的变动. (整个目录数据)
 */
@Log4j2
public class FSEditLog {

    /// 这个是全局的txid
    private long txidSeq = 0L;

    // 缓冲区
    private final DoubleBuffer doubleBuffer;
    // 正在sync
    private volatile boolean isSyncing = false;
    // 计划sync/需要sync
    private volatile boolean isSchedulingSync = false;
    // 同步的最大txid
    private volatile Long syncMaxTxId = 0L;

    // 分段: 不同线程有自己的id
    private ThreadLocal<Long> localTxId = new ThreadLocal<>();

    public FSEditLog() {
        this.doubleBuffer = new DoubleBuffer();
    }


    /**
     * Write an operation to the edit log
     * 写log到缓冲区里面.
     */
    public void logEdit(String log) {
        synchronized (this) {
            // 等待正在调度的刷盘操作
            waitSchedulingSync(100);

            long txid = ++txidSeq;
            EditLog editLog = new EditLog(txid, log);
            doubleBuffer.write(editLog);
            // 更新当前线程的最大txid
            localTxId.set(txid);

            // FIXME: 下面四句怎么看怎么别扭,
            // trigger一下doubleBuffer的刷盘
            if (!doubleBuffer.shouldSyncToDisk()) {
                return;
            }
            // isSchedulingSync = true; 这个标记符不要在这里直接设置, 它的功能是包含在syncBuffer()函数里面的.
        }

        syncBuffer(); // trigger一下
    }

    /**
     * 等待刷盘结束???? 有这个必要么?
     */
    private void waitSchedulingSync(int waitTime) {
        try {
            while (isSchedulingSync) {
                wait(waitTime);
            }
        } catch (InterruptedException e) {
            log.error("thread was waiting sync Buffer, but was interrupted", e);
        }

    }

    /**
     * 尝试把buffer的数据刷一下
     * 1. 使用synchronized来保证并发.
     */
    private void syncBuffer() {
        synchronized (this) {
            // 有其他线程正在sync: 必须要进来看一下, 来保证最后一点数据也要刷出去.
            if (isSyncing) {
                // 当前线程的txid都已经刷好了
                if (localTxId.get() < syncMaxTxId) {
                    return;
                }
                // 如果有别的线程在等, 直接返回.
                if (isSchedulingSync) {
                    return;
                }
                isSchedulingSync = true;
                while (isSyncing) {
                    try {
                        wait(2000);
                    } catch (InterruptedException e) {
                        // 被打断也要刷剩下的一点福根.
                        log.error(e);
                    }
                }
                isSchedulingSync = false;
            }

            // 交换两块buffer
            doubleBuffer.readyToSync();
            // 这里修改成当前线程的最大txId.
            // syncMaxTxId = localTxId.get(); // FIXME: 课程写法, 这里有问题, 可能拿到的是过期的txId, 还是原来的好
            if ((syncMaxTxId = doubleBuffer.getSyncBufferLatest()) == null) {
                return;
            }
            isSyncing = true;
        }

        // 开始flush: 这个放在锁外面, 因为很耗时.
        doubleBuffer.flush();

        synchronized (this) {
            // 刷完盘了, 修改完标志位.
            isSyncing = false;
            // 把大家都叫醒.
            notifyAll();
        }
    }

}
