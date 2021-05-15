package cn.gasin.dfs.backup;

import cn.gasin.dfs.backup.fetch.EditLogFetcher;
import lombok.extern.log4j.Log4j2;

/**
 * amazing-dfs中的namenode-backup, 负责从namenode中同步过来editsLog, 提供dfs的HA保证.
 *
 * @author wangyk
 */
@Log4j2
public class BackupNode {

    private volatile boolean shouldRun;
    private EditLogFetcher editLogFetcher;
    private FSNameSystemBackup fsNameSystemBackup;

    public BackupNode() {
        shouldRun = true;
        // fsNameSystem: 文件系统元数据, 不需要初始化.
        fsNameSystemBackup = new FSNameSystemBackup();
    }

    public void start() {
        editLogFetcher = new EditLogFetcher(this, fsNameSystemBackup);
        editLogFetcher.start();
    }

    public synchronized void join() {
        while (shouldRun) {
            try {
                wait();
            } catch (InterruptedException e) {
                // 被打断了也没有什么要特殊处理的, 我们要根据shouldRun这个flag变量来看
                log.error("backupNode was interrupted, e", e);
            }
        }
    }

    public void shutdown() {
        shouldRun = false;
        editLogFetcher.shutdown();
        fsNameSystemBackup.shutdown();

        synchronized (this) {
            notifyAll();
        }
    }


    public static void main(String[] args) {
        BackupNode backupNode = new BackupNode();
        backupNode.start();
        backupNode.join();
    }
}
