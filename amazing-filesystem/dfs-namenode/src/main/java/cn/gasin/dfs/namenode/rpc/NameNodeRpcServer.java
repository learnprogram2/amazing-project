package cn.gasin.dfs.namenode.rpc;

import cn.gasin.dfs.namenode.FSNameSystem;
import cn.gasin.dfs.namenode.NameNode;
import cn.gasin.dfs.namenode.cluster.DataNodeManager;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import static cn.gasin.dfs.namenode.config.Config.NAME_NODE_PORT;

/**
 * NameNode的rpc服务的接口
 */
@Log4j2
public class NameNodeRpcServer {

    // service to deal with requests.
    private final FSNameSystem fsNameSystem;
    // service to maintain cluster
    private final DataNodeManager dataNodeManager;

    // clusterMaintainService
    Server clusterMaintainServer;
    ClusterMaintainService clusterMaintainService;
    ClientService clientService;
    NameNodeBackupService nameNodeBackupService;

    public NameNodeRpcServer(FSNameSystem fsNameSystem, DataNodeManager dataNodeManager, NameNode nameNode) {
        this.fsNameSystem = fsNameSystem;
        this.dataNodeManager = dataNodeManager;

        clusterMaintainService = new ClusterMaintainService(dataNodeManager);
        clientService = new ClientService(fsNameSystem, nameNode);
        nameNodeBackupService = new NameNodeBackupService();
        clusterMaintainServer = ServerBuilder.forPort(NAME_NODE_PORT)
                .addService(clusterMaintainService)
                .addService(clientService)
                .addService(nameNodeBackupService)
                .build();

        // 添加系统停止时候的停止rpcServer
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                clusterMaintainServer.shutdown();
            }
        }));
    }

    public void start() throws IOException {
        log.info("nameNode rpc server starting");

        clusterMaintainServer.start();
        log.info("clusterMaintainService started!");

    }

    public void stop() {
        if (clusterMaintainServer != null) {
            clusterMaintainServer.shutdown();
        }
        fsNameSystem.shutdown();
        dataNodeManager.stop();
    }

    /** 同步停止, 阻塞方法 */
    public void stopSync() throws InterruptedException {
        if (clusterMaintainServer != null) {
            clusterMaintainServer.awaitTermination();
        }
        fsNameSystem.shutdown();
        dataNodeManager.stop();
    }


}
