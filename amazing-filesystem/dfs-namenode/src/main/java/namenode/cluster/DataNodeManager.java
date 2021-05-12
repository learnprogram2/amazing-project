package namenode.cluster;

import lombok.extern.log4j.Log4j2;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 集群中的dataNode管理
 */
@Log4j2
public class DataNodeManager {

    // 维护内存中的dataNode. 以后还可能会有各种机制, 读取配置什么的...
    private ConcurrentHashMap<Integer, DataNodeInfo> dataNodeMap = new ConcurrentHashMap<>();
    private ScheduledExecutorService dataNodeExpelTimer;

    public DataNodeManager() {
        dataNodeExpelTimer = Executors.newScheduledThreadPool(1,
                                                              r -> new Thread(r, "dataNode-expel-thread-1"));
        dataNodeExpelTimer.scheduleAtFixedRate(() -> {
            try {
                Iterator<DataNodeInfo> iterator = dataNodeMap.values().iterator();
                while (iterator.hasNext()) {
                    DataNodeInfo next = iterator.next();
                    if (System.currentTimeMillis() - next.getLatestHeartbeatTime() > 90 * 1000) {
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                log.error("error when expelling timeout dataNode");
            }
        }, 0, 30 * 1000, TimeUnit.MICROSECONDS);
    }

    /** 注册dataNode */
    public Boolean register(DataNodeInfo dataNodeInfo) {
        return dataNodeMap.putIfAbsent(dataNodeInfo.hashCode(), dataNodeInfo) == null;
    }

    /** dataNode心跳 */
    public Boolean heartbeat(DataNodeInfo dataNodeInfo) {
        DataNodeInfo dataNodeInCache = dataNodeMap.get(dataNodeInfo.hashCode());
        if (dataNodeInCache == null) {
            return false;
        }
        dataNodeInCache.setLatestHeartbeatTime(System.currentTimeMillis());
        return true;
    }
}
