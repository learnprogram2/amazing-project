package cn.gasin.dfs.backup;

import cn.gasin.dfs.namenode.directory.FSDirectory;
import lombok.extern.log4j.Log4j2;

/**
 * backup node 中的fsNameSystem, 负责存储从nameNode那边同步过来的namespace元数据.
 */
@Log4j2
public class FSNameSystemBackup {

    private FSDirectory fsDirectory;

    public FSNameSystemBackup() {
        fsDirectory = new FSDirectory();
    }

    public void shutdown() {

    }

    public void mkdir(String path) throws Exception {
        fsDirectory.mkdir(path);
    }
}
