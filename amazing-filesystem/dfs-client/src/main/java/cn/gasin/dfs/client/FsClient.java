package cn.gasin.dfs.client;

import cn.gasin.dfs.rpc.namenode.grpc.ClientAPIGrpc;
import cn.gasin.dfs.rpc.namenode.service.MkdirRequest;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

/**
 * client端的tool, 可以理解为工具.
 * note: 对同一个amazing-dfs的链接请使用同一个FsClient, 自己维护FsClient的单例性.
 */
public class FsClient {
    private static final String NAME_NODE_HOSTNAME = "localhost";
    private static final int NAME_NODE_PORT = 50070;

    private final ClientAPIGrpc.ClientAPIBlockingStub nameNodeServer;

    public FsClient() {
        // TODO 读取配置/构造器传入配置, 拿到nameNode的地址什么的

        // create rpc tool: 正式启动fsClient的链接, 可以证实用了.
        ManagedChannel channel = NettyChannelBuilder.forAddress(NAME_NODE_HOSTNAME, NAME_NODE_PORT).negotiationType(NegotiationType.PLAINTEXT).build();
        nameNodeServer = ClientAPIGrpc.newBlockingStub(channel);
    }

    /**
     *
     */
    public void mkdir(String path) {
        nameNodeServer.mkdir(MkdirRequest.newBuilder().setPath(path).build());
    }
}
