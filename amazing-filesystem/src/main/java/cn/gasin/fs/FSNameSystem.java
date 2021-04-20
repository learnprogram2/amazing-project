package cn.gasin.fs;

import cn.gasin.fs.directory.FSDirectory;
import cn.gasin.fs.editslog.FSEditLog;
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
        fsDirectory.mkdir(path);
        fsEditLog.logEdit("mkdir:" + path);
        return true;
    }
}
