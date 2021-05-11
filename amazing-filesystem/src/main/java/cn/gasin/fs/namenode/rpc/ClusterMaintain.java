package cn.gasin.fs.namenode.rpc;

/**
 * 集群维护的接口
 */
public interface ClusterMaintain {
    /**
     * dataNode register to nameNode
     */
    Boolean register(String ip, Integer port, String hostname);

}
