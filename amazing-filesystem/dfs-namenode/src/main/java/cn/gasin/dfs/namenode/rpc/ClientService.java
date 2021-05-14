package cn.gasin.dfs.namenode.rpc;

import cn.gasin.dfs.namenode.FSNameSystem;
import cn.gasin.dfs.namenode.NameNode;
import cn.gasin.dfs.rpc.namenode.grpc.ClientAPIGrpc;
import cn.gasin.dfs.rpc.namenode.service.MkdirRequest;
import cn.gasin.dfs.rpc.namenode.service.MkdirResponse;
import cn.gasin.dfs.rpc.namenode.service.ShutdownRequest;
import cn.gasin.dfs.rpc.namenode.service.ShutdownResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;

/**
 * client service: 和client之间的交互
 */
@Log4j2
public class ClientService extends ClientAPIGrpc.ClientAPIImplBase {
    private final FSNameSystem fsNameSystem;
    private final NameNode nameNode;

    private volatile boolean shouldRun;

    public ClientService(FSNameSystem fsNameSystem, NameNode nameNode) {
        this.fsNameSystem = fsNameSystem;
        this.nameNode = nameNode;
        shouldRun = true;
    }

    /**
     * API: make directory.
     */
    @Override
    public void mkdir(MkdirRequest request, StreamObserver<MkdirResponse> responseObserver) {
        if (!shouldRun) {
            log.info("client service has been closed, please make sure nameNode works good~");
            return;
        }
        if (fsNameSystem.mkdir(request.getPath()))
            responseObserver.onNext(MkdirResponse.newBuilder().setStatus(0).setMessage("").build());
        else
            responseObserver.onNext(MkdirResponse.newBuilder().setStatus(1).setMessage("").build());
        responseObserver.onCompleted();
    }

    @Override
    public void shutdown(ShutdownRequest request, StreamObserver<ShutdownResponse> responseObserver) {
        // TODO: 这个要校验这个client的权限够不够
        request.getClientName();

        shouldRun = false;

        // 这里不能直接把rpc系统直接关闭, 因为还需要做一些系统之间的啊什么的, 不能立即关闭,
        // 自己只是rpc的一个service, 不能关闭, 应该通知nameNode系统该关闭了
        // TODO: 还要干一些别的事情, 才能把整个nameNode和dataNode都shutdown.
        // 通知NameNode系统应该关闭了
        nameNode.shutdown();
    }
}
