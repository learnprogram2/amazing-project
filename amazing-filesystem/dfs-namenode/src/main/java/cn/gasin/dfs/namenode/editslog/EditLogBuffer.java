package cn.gasin.dfs.namenode.editslog;

/**
 * 单个buffer
 */
public class EditLogBuffer {

    /**
     * 维护最后一个editLog
     */
    public EditLog getLast() {
        return null;
    }

    /**
     * 把缓冲区的数据刷到磁盘
     */
    public void flush() {

    }

    /**
     * 当前缓冲区大小
     */
    public int size() {
        return -1;
    }

    /**
     * 添加到缓冲区一条数据
     */
    public void offer(EditLog editLog) {

    }
}
