package cn.gasin.client;

import cn.gasin.api.server.InstanceInfo;
import cn.gasin.client.http.HttpClient;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

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
    @Getter
    private Map<String, Map<String, InstanceInfo>> registry;

    private final RegisterClientWorker registerClientWorker;
    private final HttpClient httpClient;

    public Registry(RegisterClientWorker registerClientWorker, HttpClient httpClient) {
        this.registerClientWorker = registerClientWorker;
        this.httpClient = httpClient;
    }

    @Override
    public void run() {
        super.run();

        // 每30s拉取注册表
        while (registerClientWorker.running()) {
            try {
                // 直接拿来替换, 暂时不过滤啊做别的处理.
                registry = httpClient.fetchRegistry();
                log.info("fetch registry success, contains [{}] service.", registry.size());

            } catch (Exception e) {
                log.error("拉去注册表失败", e);
            } finally {
                try {
                    sleep(REGISTRY_FETCH_INTERVAL);
                } catch (InterruptedException e) {
                    log.error("拉去注册表, sleep被唤醒.");
                    Thread.interrupted();
                }
            }
        }
    }
}
