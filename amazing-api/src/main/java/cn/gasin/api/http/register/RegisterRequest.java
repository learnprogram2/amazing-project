package cn.gasin.api.http.register;

import cn.gasin.api.http.RequestType;
import lombok.*;


/**
 * 注册请求, 唯一标识一个client的标识.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    RequestType requestType;

    // 服务坐标: name&id
    String serviceName;
    String instanceId; // 这个instanceID, 我觉得就把hostName和instanceID结合在一起了吧~ 还需要hostName干什么呢?
    // String hostName; 暂时不需要这个定位了.

    // 地址坐标: instance address
    String instanceIp;
    Integer instancePort;

}
