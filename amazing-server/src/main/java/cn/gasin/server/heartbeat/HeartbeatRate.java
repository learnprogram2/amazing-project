package cn.gasin.server.heartbeat;

/**
 * 心跳计数服务:
 * FIXME: 暂时用synchronized来控制并发问题
 */
public class HeartbeatRate {
    //    这个实现没有考虑一分钟内没有心跳得时候, 如果没有的话, count就没有意义了.
    //    private int lastMinuteCount;
    //    private long lastMinuteStartTimestamp;
    //
    //    public void count() {
    //        if (System.currentTimeMillis() - lastMinuteStartTimestamp > 60 * 1000) {
    //            lastMinuteStartTimestamp = System.currentTimeMillis();
    //            lastMinuteCount = 0;
    //        }
    //        lastMinuteCount++;
    //    }
    private int lastMinuteCount;
    private long currentMinuteStartTimestamp;
    private int currentMinuteCount;

    public synchronized void count() {
        newMimute();
        currentMinuteCount++;
    }

    /** 拿到最近完整的一分钟内的计数: 距离当下可能是0-59s */
    public synchronized int getLastMinuteCount() {
        newMimute();
        return lastMinuteCount;
    }

    private void newMimute() {
        if (System.currentTimeMillis() - currentMinuteStartTimestamp >= 60 * 1000) {
            lastMinuteCount = currentMinuteCount;
            currentMinuteCount = 0;
            currentMinuteStartTimestamp = System.currentTimeMillis();
        }
    }

}
