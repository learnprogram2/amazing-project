package cn.gasin.server.registry;

import cn.gasin.api.http.heartbeat.HeartbeatRequest;
import cn.gasin.api.server.InstanceInfo;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 内存中维护的注册表.
 */

@Log4j2
@Getter
@Service // spring里面的单例. 不用写单例模式了.
public class Registry {
    /**
     * 注册表: Map<serviceName: Map<instanceId, RegistryInfo>>
     */
    private final Map<String, Map<String, InstanceInfo>> registry = new ConcurrentHashMap<>();

    public synchronized void register(InstanceInfo instanceInfo) {
        Map<String, InstanceInfo> serverMap = registry.get(instanceInfo.getServiceName());
        if (serverMap == null) {
            registry.putIfAbsent(instanceInfo.getServiceName(), new ConcurrentHashMap<>());
            serverMap = registry.get(instanceInfo.getServiceName());
        }
        // put
        serverMap.put(instanceInfo.getInstanceId(), instanceInfo);
    }


    /** 心跳服务接口 */
    public boolean heartbeat(HeartbeatRequest req) {
        Map<String, InstanceInfo> serviceMap = registry.get(req.getServiceName());
        if (Objects.nonNull(serviceMap) && serviceMap.containsKey(req.getInstanceId())) {
            InstanceInfo instanceInfo = serviceMap.get(req.getInstanceId()); // 一种极端的, 这里拿到的instanceInfo是null
            instanceInfo.renew();
            return true;
        }
        return false;
    }
}
