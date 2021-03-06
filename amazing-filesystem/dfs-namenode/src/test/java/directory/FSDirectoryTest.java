package directory;

import cn.gasin.dfs.namenode.directory.FSDirectory;
import cn.gasin.dfs.namenode.directory.model.DirectoryNode;
import org.junit.jupiter.api.Test;

class FSDirectoryTest {

    /**
     * 简单创建文件树成功.
     */
    @Test
    public void mkdir() throws Exception {
        FSDirectory fsDirectory = new FSDirectory();
        fsDirectory.mkdir("/root/fds/ewr/23");
        DirectoryNode root = fsDirectory.getRoot();
        System.out.println(root);
    }
}