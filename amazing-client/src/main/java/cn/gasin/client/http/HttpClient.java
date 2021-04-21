package cn.gasin.client.http;

import cn.gasin.api.http.Response;
import cn.gasin.api.http.heartbeat.HeartbeatRequest;
import cn.gasin.api.http.register.RegisterRequest;
import cn.gasin.api.server.InstanceInfo;
import cn.gasin.api.server.InstanceInfoChangedHolder;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static cn.gasin.client.config.ClientConfig.*;

/**
 * TODO http client, 功能类, 需要完善Http接口调用功能.
 */
@Log4j2
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

    /** 下线client */
    public void instanceOffline() {
        log.info("client下线");
        RegisterRequest registerRequest = RegisterRequest.builder()
                .serviceName(SERVICE_NAME).instanceId(INSTANCE_ID)
                .instanceIp(INSTANCE_IP).instancePort(INSTANCE_PORT).build();
    }


    // HttpClient关闭
    public void shutDown() {
        log.info("httpClient 销毁");

        // 调用server的下线接口.
    }

    /** TODO: 拉取增量注册表, 待实现哈 */
    public List<InstanceInfoChangedHolder> fetchDeltaRegistry() {
        return new LinkedList<>();
    }
}
