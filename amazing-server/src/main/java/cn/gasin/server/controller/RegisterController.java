package cn.gasin.server.controller;

import cn.gasin.api.http.Response;
import cn.gasin.api.http.heartbeat.HeartbeatRequest;
import cn.gasin.api.http.register.QueryRegistryResponse;
import cn.gasin.api.http.register.RegisterRequest;
import cn.gasin.api.server.InstanceInfo;
import cn.gasin.server.heartbeat.HeartbeatRate;
import cn.gasin.server.heartbeat.SelfProtectionPolicy;
import cn.gasin.server.registry.Registry;
import cn.gasin.server.registry.RegistryUpdatesCache;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController("/")
public class RegisterController {

    @Autowired
    private Registry registry;
    @Autowired
    private RegistryUpdatesCache registryUpdatesCache;
    @Autowired
    private HeartbeatRate heartbeatRate;
    @Autowired
    private SelfProtectionPolicy selfProtectionPolicy;

    /**
     * 注册接口
     *
     * @param req instance信息
     */
    @PostMapping("/register")
    public Response register(@RequestBody RegisterRequest req) {
        // register
        InstanceInfo instanceInfo = InstanceInfo.copyFrom(req);
        registry.register(instanceInfo);
        // 更新阈值
        selfProtectionPolicy.instanceRegister();

        return Response.success(null);
    }

    /**
     * 心跳接口, 这个最后要抽象到heartbeat portal注册一个heartbeatHandler里面去处理整个逻辑.
     *
     * @param req instance信息
     */
    @PostMapping("/heartbeat")
    public Response register(@RequestBody HeartbeatRequest req) {
        // heartbeat
        if (!registry.heartbeat(req)) {
            return Response.failed("instance haven't in registry! please register first.");
        }

        // 心跳成功, 计数
        heartbeatRate.count();

        return Response.success(null);
    }

    /** 下线client */
    @PutMapping("/instanceOffline")
    public Response instanceOffline(@RequestBody RegisterRequest req) {
        log.info("接收到client下线请求: {}", req);

        if (registry.instanceOffline(req)) {
            // 更新自我保护阈值
            selfProtectionPolicy.instanceDead();

            return Response.success(null);
        }
        return Response.failed("下线失败");
    }

    /** 拿全量注册表 */
    @GetMapping("/registry")
    public QueryRegistryResponse getAllRegistry() {
        QueryRegistryResponse response = QueryRegistryResponse.success(null);
        response.setInstanceInfoMap(registry.getRegistryCopy());
        return response;
    }

    /** 拿增量注册表 */
    @GetMapping("/registry/delta")
    public QueryRegistryResponse getDeltaRegistry() {
        QueryRegistryResponse response = QueryRegistryResponse.success(null);
        response.setDeltaInstanceInfoList(registryUpdatesCache.getRecentlyChangedQueue());
        return response;
    }


}
