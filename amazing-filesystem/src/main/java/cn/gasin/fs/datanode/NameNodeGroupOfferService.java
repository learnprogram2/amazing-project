package cn.gasin.fs.datanode;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 向NameNode注册的服务.
 */
@Log4j2
public class NameNodeGroupOfferService {

    private volatile boolean shouldRun;

    // 和主nameNode通讯的actor组件
    private NameNodeServiceActor activeServiceActor;
    // 和standby-nameNode通讯的actor组件
    private NameNodeServiceActor standbyServiceActor;

    private List<NameNodeServiceActor> serviceActorList = new CopyOnWriteArrayList<>();


    public NameNodeGroupOfferService() {
        shouldRun = true;
        // net IO actor
        activeServiceActor = new NameNodeServiceActor();
        standbyServiceActor = new NameNodeServiceActor();
        serviceActorList.add(activeServiceActor);
        serviceActorList.add(standbyServiceActor);
    }

    /** 启动对NameNode的通讯服务 */
    public void start() {
        // 注册自己
        while (shouldRun && !register()) {
            log.warn("register wailed, retry.");
        }
        // 心跳
        startHeartbeat();
    }

    /** 启动actor的注册 */
    private boolean register() {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            activeServiceActor.register(countDownLatch);
            standbyServiceActor.register(countDownLatch);
            countDownLatch.await();
            return true;
        } catch (InterruptedException e) {
            log.error("nameNode group offer service register failed because interrupted.", e);
            return false;
        }
    }

    /** 启动actor的心跳机制 */
    private void startHeartbeat() {
        if (shouldRun) {
            for (NameNodeServiceActor nNActor : serviceActorList) {
                nNActor.startHeartbeat();
            }
        }
    }


    // 停止集群服务
    public void stop() {
        shouldRun = false;
        for (NameNodeServiceActor nameNodeServiceActor : serviceActorList) {
            nameNodeServiceActor.stop();
        }
    }

}
