package cn.gasin.dfs.namenode;

import cn.gasin.dfs.namenode.directory.FSDirectory;
import cn.gasin.dfs.namenode.editslog.FSEditLog;
import lombok.extern.log4j.Log4j2;

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
    public Boolean mkdir(String path) {
        path = path.trim();
        try {
            fsDirectory.mkdir(path);
            fsEditLog.logEdit("{\"OP\": \"MKDIR\", \"PATH\": \"" + path + "\"}");
            return true;
        } catch (Exception e) {
            // todo 回退
            log.error("mkdir failed:", e);
            return false;
        }
    }

    public void stop() {
// C:\Users\yw31830\IdeaProjects\amazing-project\amazing-filesystem\dfs-namenode\target\classes\editLogs\11215 (The system cannot find the path specified)
    }
}
