package cn.gasin.dfs.namenode.rpc;

import cn.gasin.dfs.rpc.namenode.grpc.NameNodeBackupAPIGrpc;
import cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest;
import cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;

/**
 * server端的NameNodeBackupAPI服务, 负责接收BackupNode的同步请求
 */
@Log4j2
public class NameNodeBackupService extends NameNodeBackupAPIGrpc.NameNodeBackupAPIImplBase {
    @Override
    public void fetchEditLog(FetchEditLogRequest request, StreamObserver<FetchEditLogResponse> responseObserver) {

    }
}
