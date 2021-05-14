package cn.gasin.dfs.namenode.editslog;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;

/**
 * 一条log
 */
@Log4j2
@Getter
public class EditLog {
    private Long txid;
    private String content;

    public EditLog(long txid, String content) {
        this.txid = txid;
        this.content = content;
    }

    public byte[] getBytes() {
        return content.getBytes(StandardCharsets.UTF_8);
    }
}
