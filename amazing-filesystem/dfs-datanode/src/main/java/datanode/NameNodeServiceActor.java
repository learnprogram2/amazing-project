package datanode;

import cn.gasin.dfs.rpc.namenode.service.NameNodeServer;
import cn.gasin.dfs.rpc.namenode.service.RegisterRequest;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;

/**
 * 通讯组件
 */
@Log4j2
public class NameNodeServiceActor {

    private ScheduledExecutorService heartbeatTimer;

    private NameNodeServer nameNodeServer;

    // private NameNode nameNode;

    public NameNodeServiceActor() {
        heartbeatTimer = Executors.newScheduledThreadPool(1,
                                                          r -> new Thread(r, "nnActor-heartbeat-executor-schedule-thread"));

    }

    /**
     * 向nameNode注册, TODO 还没有具体实现, 还没有TCP框架支持和NameNode通讯地址.
     */
    public void register(CountDownLatch countDownLatch) {
        CompletableFuture<Void> registerRes = CompletableFuture.runAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                // 通讯
                log.info("register to NameNode...");

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
                        log.info("start heartbeat to nn:{}");
                        // 1. 拿自己的config
                        String ip = "192.168.0.1";
                        Integer port = 9001;
                        String hostname = "dfs-data-01";
                        // TODO: RPC发送到nameNode的注册接口上

                    } catch (Exception e) {
                        log.error("heartbeat occurred one error: ", e);
                    }
                },
                0, 1000, TimeUnit.MICROSECONDS
        );
    }
}
