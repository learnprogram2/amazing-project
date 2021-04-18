package cn.gasin.client.http;

import cn.gasin.api.http.Response;
import cn.gasin.api.http.heartbeat.HeartbeatRequest;
import cn.gasin.api.http.register.RegisterRequest;
import cn.gasin.api.server.InstanceInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO http client, 功能类, 需要完善Http接口调用功能.
 */
public class HttpClient {
    public Response sendRegisterRequest(RegisterRequest registerRequest) {
        return null;
    }

    public Response sendHeartbeat(HeartbeatRequest heartbeatRequest) {
        return null;
    }

    /** 拉取注册表 */
    public Map<String, Map<String, InstanceInfo>> fetchRegistry() {
        InstanceInfo instanceInfo = new InstanceInfo();
        instanceInfo.setInstanceIp("192.168.0.1");
        instanceInfo.setInstanceId("instanceId");
        instanceInfo.setInstancePort(8080);
        instanceInfo.setServiceName("serviceName1");

        Map<String, Map<String, InstanceInfo>> registry = new HashMap<>();

        Map<String, InstanceInfo> serviceMap = new HashMap<>();

        registry.put("serviceName1", serviceMap);
        serviceMap.put(instanceInfo.getInstanceId(), instanceInfo);

        return registry;
    }
}
