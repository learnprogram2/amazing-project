package cn.gasin.api.http.heartbeat;

import lombok.*;

/**
 * 心跳请求: 只需要发送自己的坐标就好了吧~
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartbeatRequest {

    // 服务坐标: name&id
    String serviceName;
    String instanceId;
}
