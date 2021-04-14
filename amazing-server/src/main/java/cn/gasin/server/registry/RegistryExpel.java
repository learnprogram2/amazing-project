package cn.gasin.server.registry;

import cn.gasin.api.server.InstanceInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

//

/**
 * check instanceInfo status, expel dead instanceInfo.
 */
@Log4j2
@AllArgsConstructor
public class RegistryExpel extends Thread {
    private final Registry registry;
    private static final long INTERNAL = 60 * 1000;
    private static final Map<String, InstanceInfo> EMPTY_MAP = new HashMap<>();

    //开始工作, 驱逐过期的instanceInfo
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(INTERNAL);

                Map<String, Map<String, InstanceInfo>> registry = this.registry.getRegistry();
                for (String serviceName : registry.keySet()) {
                    Map<String, InstanceInfo> serviceMap = registry.getOrDefault(serviceName, EMPTY_MAP);
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
