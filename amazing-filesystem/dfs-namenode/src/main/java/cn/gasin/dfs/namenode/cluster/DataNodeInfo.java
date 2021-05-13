package cn.gasin.dfs.namenode.cluster;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * DataNode的信息
 */
@Getter
@Setter
public class DataNodeInfo {
    private String ip;
    private Integer port;
    private String hostname;
    private Long latestHeartbeatTime = System.currentTimeMillis();

    public DataNodeInfo(String ip, Integer port, String hostname) {
        this.ip = ip;
        this.port = port;
        this.hostname = hostname;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, hostname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataNodeInfo that = (DataNodeInfo) o;
        return ip.equals(that.ip) && port.equals(that.port) && hostname.equals(that.hostname);
    }

}
