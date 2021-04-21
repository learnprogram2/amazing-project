package cn.gasin.client;

import cn.gasin.api.server.InstanceInfo;
import cn.gasin.api.server.InstanceInfoChangedHolder;
import cn.gasin.client.http.HttpClient;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.gasin.client.config.ClientConfig.REGISTRY_FETCH_INTERVAL;

/**
 * client端的注册表
 */
@Log4j2
public class Registry extends Thread {
    /**
     * 注册表: Map<serviceName: Map<instanceId, RegistryInfo>>
     */
    private Map<String, Map<String, InstanceInfo>> registry;

    private final RegisterClientWorker registerClientWorker;
    private final HttpClient httpClient;

    private volatile boolean initialize = false;

    public Registry(RegisterClientWorker registerClientWorker, HttpClient httpClient) {
        this.registerClientWorker = registerClientWorker;
        this.httpClient = httpClient;
    }

    @Override
    public void run() {
        super.run();

        // 初始化拉全部注册表
        while (!initialize) {
            registry = httpClient.fetchRegistry();
            initialize = true;
        }

        // 每30s拉取注册表
        while (registerClientWorker.running()) {
            try {
                synchronized (registry) {
                    // 直接拿来替换, 暂时不过滤啊做别的处理.
                    mergeIntoRegistry(httpClient.fetchDeltaRegistry());
                    log.info("fetch registry success, contains [{}] service.", registry.size());
                }
            } catch (Exception e) {
                log.error("拉去注册表失败", e);
            } finally {
                try {
                    sleep(REGISTRY_FETCH_INTERVAL);
                } catch (InterruptedException e) {
                    log.error("拉去注册表, sleep被唤醒.");
                    if (Thread.interrupted())
                        break;
                }
            }
        }
    }

    private void mergeIntoRegistry(List<InstanceInfoChangedHolder> fetchDeltaRegistry) {
        if (fetchDeltaRegistry.size() == 0) {
            log.info("no registry updates.");
            return;
        }

        while (fetchDeltaRegistry.size() > 0) {
            for (InstanceInfoChangedHolder infoChangedHolder : fetchDeltaRegistry) {
                InstanceInfo instanceInfo = infoChangedHolder.getInstanceInfo();
                // 新服务直接加进去
                if (!registry.containsKey(instanceInfo.getServiceName())) {
                    Map<String, InstanceInfo> serviceMap = new HashMap<>();
                    serviceMap.put(instanceInfo.getInstanceId(), instanceInfo);
                    registry.put(instanceInfo.getServiceName(), serviceMap);
                    continue;
                }
                Map<String, InstanceInfo> serviceMap = registry.get(instanceInfo.getServiceName());
                if (!serviceMap.containsKey(instanceInfo.getInstanceId())) {
                    serviceMap.put(instanceInfo.getInstanceId(), instanceInfo);
                } else {
                    InstanceInfo contained = serviceMap.get(instanceInfo.getInstanceId());
                    if (!contained.equals(instanceInfo)) {
                        // todo: 这里要判断更新的时间, 不能老的把新的替换掉了.
                        serviceMap.put(instanceInfo.getInstanceId(), instanceInfo);
                    }
                }
            }
        }

    }

    public void shutDown() {
        registry = null;
        this.interrupt();
    }

    public Map<String, Map<String, InstanceInfo>> getRegistry() {
        // todo: 要防止并发修改, 拿出去查询就好了, 别搞修改.
        return registry;
    }
}
