package cn.gasin.dfs.namenode.rpc;

import cn.gasin.dfs.rpc.namenode.grpc.ClientAPIGrpc;
import cn.gasin.dfs.rpc.namenode.service.MkdirRequest;
import cn.gasin.dfs.rpc.namenode.service.MkdirResponse;
import io.grpc.stub.StreamObserver;
import cn.gasin.dfs.namenode.FSNameSystem;

/**
 * client service: 和client之间的交互
 */
public class ClientService extends ClientAPIGrpc.ClientAPIImplBase {
    private final FSNameSystem fsNameSystem;

    public ClientService(FSNameSystem fsNameSystem) {
        this.fsNameSystem = fsNameSystem;
    }

    /**
     * API: make directory.
     */
    @Override
    public void mkdir(MkdirRequest request, StreamObserver<MkdirResponse> responseObserver) {
        if (fsNameSystem.mkdir(request.getPath()))
            responseObserver.onNext(MkdirResponse.newBuilder().setStatus(0).setMessage("").build());
        else
            responseObserver.onNext(MkdirResponse.newBuilder().setStatus(1).setMessage("").build());
        responseObserver.onCompleted();
    }
}
