package namenode.rpc;

import namenode.FSNameSystem;
import namenode.cluster.DataNodeInfo;
import namenode.cluster.DataNodeManager;
import lombok.extern.log4j.Log4j2;

/**
 * NameNode的rpc服务的接口
 */
@Log4j2
public class NameNodeRpcServer implements ClusterMaintain {
    // service to deal with requests.
    private FSNameSystem fsNameSystem;
    // service to maintain cluster
    private DataNodeManager dataNodeManager;

    public NameNodeRpcServer(FSNameSystem fsNamesystem, DataNodeManager dataNodeManager) {
        this.fsNameSystem = fsNamesystem;
        this.dataNodeManager = dataNodeManager;
    }

    public void start() {
        log.info("namenode rpc server starting");
        // todo: implements this method to complete this rpcServer.
    }

    /**
     * API: make directory.
     *
     * @param path: the path of the new directory.
     */
    public Boolean mkdir(String path) throws Exception {
        return fsNameSystem.mkdir(path);
    }


    @Override
    public Boolean register(String ip, Integer port, String hostname) {
        return dataNodeManager.register(new DataNodeInfo(ip, port, hostname));
    }

    @Override
    public Boolean heartbeat(String ip, Integer port, String hostname) {
        return dataNodeManager.heartbeat(new DataNodeInfo(ip, port, hostname));
    }
}
