package cn.gasin.dfs.namenode.config;

public class Config {
    // ========================== nameNode config
    public static final String NAME_NODE_HOSTNAME = "localhost";
    public static final Integer NAME_NODE_PORT = 50070;


    // logBuffer刷盘的容量阈值.
    public static final int EDIT_LOG_BUFFER_SIZE_SYNC_THRESHOLD = 25 * 1024;




    // ============================= cn.gasin.dfs.datanode config
    public static final String DATA_NODE_IP = "192.168.0.1";
    public static final int DATA_NODE_PORT = 50071;
    public static final String DATA_NODE_HOSTNAME = "dfs-cn.gasin.dfs.datanode-01";

}
