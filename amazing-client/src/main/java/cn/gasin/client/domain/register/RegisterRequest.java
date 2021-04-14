package cn.gasin.client.domain.register;

import cn.gasin.client.domain.RequestType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * 注册请求, 唯一标识一个client的标识.
 */
@Builder
@Getter
@Setter
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
