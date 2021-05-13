package cn.gasin.dfs.namenode.rpc;

import cn.gasin.dfs.rpc.namenode.grpc.ClusterMaintainAPIGrpc;
import cn.gasin.dfs.rpc.namenode.service.HeartbeatRequest;
import cn.gasin.dfs.rpc.namenode.service.HeartbeatResponse;
import cn.gasin.dfs.rpc.namenode.service.RegisterRequest;
import cn.gasin.dfs.rpc.namenode.service.RegisterResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import cn.gasin.dfs.namenode.cluster.DataNodeInfo;
import cn.gasin.dfs.namenode.cluster.DataNodeManager;

/**
 * 集群维护的接口
 */
@Log4j2
public class ClusterMaintainService extends ClusterMaintainAPIGrpc.ClusterMaintainAPIImplBase {
    // service to maintain cluster
    private DataNodeManager dataNodeManager;

    public ClusterMaintainService(DataNodeManager dataNodeManager) {
        this.dataNodeManager = dataNodeManager;
    }

    /**
     * dataNode register to nameNode
     */
    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        Boolean succ = dataNodeManager.register(new DataNodeInfo(request.getIp(), request.getPort(), request.getHostname()));

        // TODO: RegisterResponse添加msg字段, 然后定义status enum
        if (succ) {
            responseObserver.onNext(RegisterResponse.newBuilder().setStatus(0).build());
        } else {
            responseObserver.onNext(RegisterResponse.newBuilder().setStatus(1).build());
        }
        responseObserver.onCompleted();
    }

    /**
     * dataNode send heartbeat to nameNode
     */
    @Override
    public void heartbeat(HeartbeatRequest request, StreamObserver<HeartbeatResponse> responseObserver) {
        Boolean succ = dataNodeManager.heartbeat(new DataNodeInfo(request.getIp(), request.getPort(), request.getHostname()));
        if (succ) {
            responseObserver.onNext(HeartbeatResponse.newBuilder().setStatus(0).build());
        } else {
            responseObserver.onNext(HeartbeatResponse.newBuilder().setStatus(1).build());
        }
        responseObserver.onCompleted();
    }


}
