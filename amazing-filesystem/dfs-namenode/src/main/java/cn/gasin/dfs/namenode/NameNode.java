package cn.gasin.dfs.namenode;


import cn.gasin.dfs.namenode.cluster.DataNodeManager;
import cn.gasin.dfs.namenode.rpc.NameNodeRpcServer;
import lombok.extern.log4j.Log4j2;

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
        this.rpcServer = new NameNodeRpcServer(fsNamesystem, dataNodeManager, this);
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

    /**
     * 把各个组件都关掉
     */
    public void shutdown() {
        // 1. 把大家都叫醒, 该退出了
        shouldRun = false;
        synchronized (this) {
            notifyAll();
        }
        // 2. 把组件都关闭.
        fsNamesystem.shutdown();
        dataNodeManager.shutdown();

        // TODO 3. 再做一些什么, 把集群都关闭的事情.

        // 4. 最后再关rpc
        rpcServer.stop();
    }
}
