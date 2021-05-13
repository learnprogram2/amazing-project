package cn.gasin.dfs.datanode;

import cn.gasin.dfs.rpc.namenode.grpc.ClusterMaintainAPIGrpc;
import cn.gasin.dfs.rpc.namenode.service.HeartbeatRequest;
import cn.gasin.dfs.rpc.namenode.service.HeartbeatResponse;
import cn.gasin.dfs.rpc.namenode.service.RegisterRequest;
import cn.gasin.dfs.rpc.namenode.service.RegisterResponse;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;

import static cn.gasin.dfs.datanode.config.Config.*;

/**
 * 通讯组件
 */
@Log4j2
public class NameNodeServiceActor {
    ClusterMaintainAPIGrpc.ClusterMaintainAPIBlockingStub nameNodeServer;

    private final ScheduledExecutorService heartbeatTimer;

    // private NameNode nameNode;

    public NameNodeServiceActor() {
        // create rpc tool
        ManagedChannel channel = NettyChannelBuilder.forAddress(NAME_NODE_HOSTNAME, NAME_NODE_PORT).negotiationType(NegotiationType.PLAINTEXT).build();
        nameNodeServer = ClusterMaintainAPIGrpc.newBlockingStub(channel);

        heartbeatTimer = Executors.newScheduledThreadPool(1,
                                                          r -> new Thread(r, "nnActor-heartbeat-executor-schedule-thread"));
    }

    /**
     * 向nameNode注册, FIXME: 处理首次注册不上的重试, 还有没有nameNode的停机.
     */
    public void register(CountDownLatch countDownLatch) {
        CompletableFuture<Void> registerRes = CompletableFuture.runAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                // 通讯
                log.info("register to NameNode...");

                RegisterRequest registerRequest = RegisterRequest.newBuilder().setIp(DATA_NODE_IP).setPort(DATA_NODE_PORT).setHostname(DATA_NODE_HOSTNAME).build();
                RegisterResponse response = nameNodeServer.register(registerRequest);
                if (response.getStatus() == 0) {
                    log.info("register success, {}", response);
                }
            }
        });
        registerRes.whenComplete((unused, throwable) -> countDownLatch.countDown());
    }

    public void stop() {
        if (heartbeatTimer != null) {
            heartbeatTimer.shutdown();
        }
    }

    /**
     * 启动心跳机制, 在注册好之后
     */
    public void startHeartbeat() {
        heartbeatTimer.scheduleAtFixedRate(
                () -> {
                    try {
                        log.info("start heartbeat to nn:{}", nameNodeServer);
                        // 1. 拿自己的config
                        String ip = "192.168.0.1";
                        Integer port = 9001;
                        String hostname = "dfs-data-01";
                        // RPC发送到nameNode的注册接口上
                        HeartbeatResponse heartbeatRsp = nameNodeServer.heartbeat(
                                HeartbeatRequest.newBuilder().setIp(DATA_NODE_IP).setPort(DATA_NODE_PORT).setHostname(DATA_NODE_HOSTNAME).build()
                        );
                        if (heartbeatRsp.getStatus() != 0) {
                            log.warn("heartbeat failed:{}", heartbeatRsp);
                        } else {
                            log.info("heartbeat succ:{}", heartbeatRsp);
                        }

                    } catch (Exception e) {
                        log.error("heartbeat occurred one error: ", e);
                    }
                },
                0, 30 * 1000, TimeUnit.MICROSECONDS
        );
    }
}
