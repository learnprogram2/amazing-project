package cn.gasin.dfs.namenode;


import lombok.extern.log4j.Log4j2;
import cn.gasin.dfs.namenode.cluster.DataNodeManager;
import cn.gasin.dfs.namenode.rpc.NameNodeRpcServer;

import java.io.IOException;

/**
 * the main class of FS.
 */
@Log4j2
public class NameNode {
    private volatile boolean shouldRun = false;

    // services
    private FSNameSystem fsNamesystem;
    private NameNodeRpcServer rpcServer;
    private DataNodeManager dataNodeManager;

    public NameNode() throws IOException {
        initialize();
        log.info("nameNode started");
    }

    /**
     * prepare all steps before running.
     */
    public void initialize() throws IOException {
        shouldRun = true;
        // maintain all the metadata.
        this.fsNamesystem = new FSNameSystem();
        // maintain cluster's cn.gasin.dfs.datanode;
        this.dataNodeManager = new DataNodeManager();

        // rpc protocol.
        this.rpcServer = new NameNodeRpcServer(fsNamesystem, dataNodeManager);
        this.rpcServer.start();
    }

    // 关闭的时候把flag关闭, 然后interrupt当前obj就好了就好了
    public synchronized void join() {
        while (shouldRun) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.info("name node was interrupted");

            }
        }
    }


    public static void main(String[] args) throws IOException {
        NameNode nn = new NameNode();
        nn.join(); // 始终阻塞在这里running.
    }

}
