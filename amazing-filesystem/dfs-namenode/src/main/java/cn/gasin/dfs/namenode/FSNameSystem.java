package cn.gasin.dfs.namenode;

import cn.gasin.dfs.namenode.directory.FSDirectory;
import cn.gasin.dfs.namenode.editslog.FSEditLog;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * nameNode的元数据
 */
@Log4j2
public class FSNameSystem {
    private FSEditLog fsEditLog;
    private FSDirectory fsDirectory;

    public FSNameSystem() {
        fsEditLog = new FSEditLog();
        fsDirectory = new FSDirectory();
    }


    /**
     * 1. record editsLog
     * 2. maintain metadata: create
     *
     * @param path path of the new directory
     */
    public boolean mkdir(String path) {
        path = path.trim();
        try {
            fsDirectory.mkdir(path);
            return fsEditLog.logEdit("{\"OP\": \"MKDIR\", \"PATH\": \"" + path + "\"}");
        } catch (Exception e) {
            // todo 回退
            log.error("mkdir failed:", e);
            return false;
        }
    }

    // FIXME: 这里怎么保证最后一条写到内存里的, 必须要写到log里?
    public void shutdown()  {
        // 先把内存的目录关掉
        fsDirectory = null; // .shutdown();
        // 目录不能修改了, 也没有editsLog了, 再把log关掉.
        fsEditLog.flush();
    }
}
