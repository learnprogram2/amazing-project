package cn.gasin.fs.namenode.cluster;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 集群中的dataNode管理
 */
public class DataNodeManager {

    // 维护内存中的dataNode. 以后还可能会有各种机制, 读取配置什么的...
    ConcurrentLinkedQueue<DataNodeInfo> dataNodeQueue = new ConcurrentLinkedQueue<>();

    /**
     * dataNode注册接口
     */
    public Boolean register(DataNodeInfo dataNodeInfo) {
        dataNodeQueue.offer(dataNodeInfo);
        return true;
    }
}
