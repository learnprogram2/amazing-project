package cn.gasin.server.registry;

import cn.gasin.api.server.InstanceInfo;
import cn.gasin.server.heartbeat.SelfProtectionPolicy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
    Thread.class
    每个线程都有优先级. 每个thread都可以变成daemon线程.
    外面线程内创建的线程对象的优先级和外面线程一样. daemon也一样.

    JVM起来的时候, 只有一个线程: main非守护线程.JVM执行直到:
    1. 调用Runtime的exit, 然后securityManager允许安全退出.
    2. 所有非守护线程都执行完了.

    创建一个县城有两种方法:
    1. 继承Thread重写run方法.
    2. 实现Runnable接口, 继承run方法. 然后把obj作为参数创建Thread. 启动Thread.

    线程都有一个名字来定位, 没指定就默认, 多个线程可以指定同一个名字.
 */
/*
    ThreadGroup.class

    线程组代表一组线程, 一个线程组还能包含另一个线程组.
    多个线程组构成一个树, 每个线程组可以指定父线程组

    一个线程可以拿到自己线程组的信息, 但是拿不到parent线程组或其他线程组的信息

    锁机制是从下往上锁group.
 */

/**
 * check instanceInfo status, expel dead instanceInfo.
 */
@Log4j2
@Component
public class RegistryExpel {
    private static final long INTERNAL = 60 * 1000;
    private static final Map<String, InstanceInfo> EMPTY_MAP = new HashMap<>();

    // 注册表
    @Autowired
    private Registry registry;

    // 自我保护机制
    @Autowired
    private SelfProtectionPolicy selfProtectionPolicy;

    // 工作线程
    private DaemonThread daemonThread;


    public void start() throws Exception {
        if (Objects.nonNull(daemonThread)) {
            throw new Exception("不能重复启动");
        }
        daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    class DaemonThread extends Thread {

        //开始工作, 驱逐过期的instanceInfo
        public void run() {
            while (true) {
                try {
                    Thread.sleep(INTERNAL);
                    // 自我保护机制.
                    while (selfProtectionPolicy.isProtectionEnabled()) {
                        Thread.sleep(INTERNAL);
                    }

                    Map<String, Map<String, InstanceInfo>> registryMap = registry.getRegistry();
                    for (String serviceName : registryMap.keySet()) {
                        Map<String, InstanceInfo> serviceMap = registryMap.getOrDefault(serviceName, EMPTY_MAP);
                        for (InstanceInfo instance : serviceMap.values()) {
                            if (!instance.isAlive()) {
                                log.warn("instance [{}] is dead", instance);
                                serviceMap.remove(instance.getInstanceId());
                                // 更新自我保护的阈值.
                                selfProtectionPolicy.instanceDead();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    log.warn("expel Registry thread was interrupted:{}. ", Thread.interrupted(), e);
                    // exit this thread.
                    return;
                } catch (Exception e) {
                    log.error("expel Registry error:", e);
                }
            }
        }
    }
}
