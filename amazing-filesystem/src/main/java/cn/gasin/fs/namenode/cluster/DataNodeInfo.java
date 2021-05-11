package cn.gasin.fs.namenode.cluster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DataNode的信息
 */
@Getter
@Setter
@AllArgsConstructor
public class DataNodeInfo {
    private String ip;
    private Integer port;
    private String hostname;
}
