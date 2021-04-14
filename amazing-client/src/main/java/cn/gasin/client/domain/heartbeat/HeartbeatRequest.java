package cn.gasin.client.domain.heartbeat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 心跳请求: 只需要发送自己的坐标就好了吧~
 */
@Getter
@Setter
@Builder
public class HeartbeatRequest {

    // 服务坐标: name&id
    String serviceName;
    String instanceId;
}
