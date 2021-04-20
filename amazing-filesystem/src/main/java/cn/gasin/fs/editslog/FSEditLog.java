package cn.gasin.fs.editslog;

/**
 * maintain edit-log.
 * 专门负责管理写入edits log到磁盘文件里去
 */
public class FSEditLog {

    /// 这个是全局的txid
    private long txidSeq = 0L;

    private final DoubleBuffer doubleBuffer;

    public FSEditLog() {
        this.doubleBuffer = new DoubleBuffer();
    }


    /**
     * Write an operation to the edit log
     * 写log到缓冲区里面.
     */
    public void logEdit(String log) {
        synchronized (this) {
            long txid = ++txidSeq;
            EditLog editLog = new EditLog(txid, log);
            doubleBuffer.write(editLog);
        }
    }

}
