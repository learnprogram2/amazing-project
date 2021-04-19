package cn.gasin.server.heartbeat;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.gasin.server.ServerConfig.EXPECT_HEARTBEAT_FREQUENCY;

/**
 * 自我保护机制实现: 防止服务过快下线
 */
@Log4j2
@Component
public class SelfProtectionPolicy {

    private int expectedHeartbeat = 0;
    private int expectedHeartbeatThreshold = 0;

    @Autowired
    private HeartbeatRate heartbeatRate;

    public synchronized void instanceDead() {
        expectedHeartbeat -= EXPECT_HEARTBEAT_FREQUENCY;
        expectedHeartbeatThreshold = expectedHeartbeat * EXPECT_HEARTBEAT_FREQUENCY;
    }

    public synchronized void instanceRegister() {
        expectedHeartbeat += EXPECT_HEARTBEAT_FREQUENCY;
        expectedHeartbeatThreshold = expectedHeartbeat * EXPECT_HEARTBEAT_FREQUENCY;
    }

    /** 自我保护机制是否开启, 即使开启了, 也应该允许手动下线哦. */
    public synchronized boolean isProtectionEnabled() {
        int lastMinuteCount = heartbeatRate.getLastMinuteCount();
        if (lastMinuteCount < expectedHeartbeatThreshold) {
            log.warn("[自我保护机制开启] lastMinuteCount:[{}], expectedHeartbeatThreshold:[{}]", lastMinuteCount, expectedHeartbeatThreshold);
            return true;
        } else {
            return false;
        }
    }
}
