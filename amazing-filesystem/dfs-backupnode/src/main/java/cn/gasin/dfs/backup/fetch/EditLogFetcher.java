package cn.gasin.dfs.backup.fetch;

import cn.gasin.dfs.backup.BackupNode;
import cn.gasin.dfs.backup.FSNameSystemBackup;
import cn.gasin.dfs.rpc.namenode.grpc.NameNodeBackupAPIGrpc;
import cn.gasin.dfs.rpc.namenode.service.FetchEditLogRequest;
import cn.gasin.dfs.rpc.namenode.service.FetchEditLogResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.gasin.dfs.backup.config.BackupConfig.NAME_NODE_HOSTNAME;
import static cn.gasin.dfs.backup.config.BackupConfig.NAME_NODE_PORT;

/**
 * backupNode中负责从nameNode中拉取editsLog的组件, 这个是入口.
 */
@Log4j2
public class EditLogFetcher {
    private volatile boolean shouldRun;
    private final ExecutorService fetcherExecutor;

    private FSNameSystemBackup fsNameSystem;
    private NameNodeBackupAPIGrpc.NameNodeBackupAPIBlockingStub backupAPI;


    public EditLogFetcher(BackupNode backupNode, FSNameSystemBackup fsNameSystem) {
        shouldRun = true;

        // 1. 初始化连接
        ManagedChannel channel = NettyChannelBuilder.forAddress(NAME_NODE_HOSTNAME, NAME_NODE_PORT).negotiationType(NegotiationType.PLAINTEXT).build();
        backupAPI = NameNodeBackupAPIGrpc.newBlockingStub(channel);

        // 2. 接收本地的fsNameSystem
        this.fsNameSystem = fsNameSystem;

        fetcherExecutor = Executors.newFixedThreadPool(1, r -> new Thread(r, "edit-log-fetcher-1"));
    }

    public void start() {
        // 1. 开启定时任务, 从namenode上fetch数据.
        fetcherExecutor.submit(() -> {
            while (shouldRun) {
                JSONArray editLogArray = fetchEditLogList();
                for (int i = 0; i < editLogArray.size(); i++) {
                    JSONObject editLog = editLogArray.getJSONObject(i);
                    // TODO 把这个option抽出来.
                    String op = editLog.getString("OP");
                    if ("mkdir".equalsIgnoreCase(op)) {
                        String path = editLog.getString("path");
                        try {
                            fsNameSystem.mkdir(path);
                        } catch (Exception e) {
                            log.error("backup deal with editLog failed, editLog:{}", editLog, e);
                        }
                    }
                }
                // 这要不要等一等....
            }
        });

    }

    /**
     * 负责从nameNode那里rpc调用拿到editLog
     * note: 我的实现和课程不一样, 课程里面抽出来了一个{NameNodeRpcClient}来调用rpc, 然后解析, 我觉得没必要...
     */
    private JSONArray fetchEditLogList() {
        // TODO: 这个request的参数的意义我还没搞明白.
        FetchEditLogRequest fetchEditLogRequest = FetchEditLogRequest.newBuilder().setBackupNodeId("xxxxxxx").build();
        FetchEditLogResponse editLogResponse = backupAPI.fetchEditLog(fetchEditLogRequest);
        return JSONArray.parseArray(editLogResponse.getEditLogJson());
    }

    public void shutdown() {
        shouldRun = false;
        fetcherExecutor.shutdown();
    }
}


