package namenode.rpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.log4j.Log4j2;
import namenode.FSNameSystem;
import namenode.cluster.DataNodeManager;

import java.io.IOException;

import static namenode.config.Config.NAME_NODE_PORT;

/**
 * NameNode的rpc服务的接口
 */
@Log4j2
public class NameNodeRpcServer {

    // service to deal with requests.
    private FSNameSystem fsNameSystem;
    // service to maintain cluster
    private DataNodeManager dataNodeManager;

    // clusterMaintainService
    Server clusterMaintainServer;
    ClusterMaintainService clusterMaintainService;

    public NameNodeRpcServer(FSNameSystem fsNamesystem, DataNodeManager dataNodeManager) {
        this.fsNameSystem = fsNamesystem;
        this.dataNodeManager = dataNodeManager;

        clusterMaintainService = new ClusterMaintainService(dataNodeManager);
        clusterMaintainServer = ServerBuilder.forPort(NAME_NODE_PORT)
                .addService(clusterMaintainService)
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
    }

    /** 同步停止, 阻塞方法 */
    public void stopSync() throws InterruptedException {
        if (clusterMaintainServer != null) {
            clusterMaintainServer.awaitTermination();
        }
    }

    /**
     * API: make directory.
     *
     * @param path: the path of the new directory.
     */
    public Boolean mkdir(String path) throws Exception {
        return fsNameSystem.mkdir(path);
    }


}
