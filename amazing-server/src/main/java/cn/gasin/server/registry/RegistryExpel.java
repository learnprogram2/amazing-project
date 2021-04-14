package cn.gasin.server.registry;

import cn.gasin.api.server.InstanceInfo;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//

/**
 * check instanceInfo status, expel dead instanceInfo.
 */
@Log4j2
public class RegistryExpel {
    private final Registry registry;
    private static final long INTERNAL = 60 * 1000;
    private static final Map<String, InstanceInfo> EMPTY_MAP = new HashMap<>();

    private DaemonThread daemonThread;

    public RegistryExpel(Registry registry) {
        this.registry = registry;
    }

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

                    Map<String, Map<String, InstanceInfo>> registryMap = registry.getRegistry();
                    for (String serviceName : registryMap.keySet()) {
                        Map<String, InstanceInfo> serviceMap = registryMap.getOrDefault(serviceName, EMPTY_MAP);
                        for (InstanceInfo instance : serviceMap.values()) {
                            if (!instance.isAlive()) {
                                log.warn("instance [{}] is dead", instance);
                                serviceMap.remove(instance.getInstanceId());
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    log.warn("expel Registry thread was interrupted:{}. ", Thread.interrupted(), e);
                } catch (Exception e) {
                    log.error("expel Registry error:", e);
                }
            }
        }
    }
}
