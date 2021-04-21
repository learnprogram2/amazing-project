package cn.gasin.server.registry;

import cn.gasin.api.server.InstanceInfo;
import cn.gasin.api.server.InstanceInfoChangedHolder;
import cn.gasin.api.server.InstanceInfoOperation;
import cn.gasin.api.server.config.ServiceConfig;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Objects;

import static cn.gasin.api.server.config.ServiceConfig.REGISTRY_UPDATES_CACHE_EXPIRE_INTERNAL;

/**
 * 注册表最新变动的缓存.
 */
@Log4j2
@Component
public class RegistryUpdatesCache {

    /**
     * 这个list修改的时候也有并发问题, 但是, 这个数据不是很重要. 而且这个list的修改很集中, 大多数都是读取.
     * TODO: 这个队列里面没有去重
     */
    @Getter
    private final LinkedList<InstanceInfoChangedHolder> recentlyChangedQueue = new LinkedList<>();

    private CacheUpdateDaemon cacheUpdateDaemon;

    public RegistryUpdatesCache() {
        this.cacheUpdateDaemon = new CacheUpdateDaemon();
        cacheUpdateDaemon.start();
    }

    /**
     * 缓存一个刚刚更新的instance, 更新的操作是operation
     */
    public void cache(InstanceInfo instanceInfo, InstanceInfoOperation operation) {
        synchronized (recentlyChangedQueue) {
            InstanceInfoChangedHolder infoChangedHolder = new InstanceInfoChangedHolder(instanceInfo, operation);
            recentlyChangedQueue.offer(infoChangedHolder);
        }
    }

    /**
     * > 一旦启动线程，便不必保留对 Thread 对象的引用。 线程将继续执行，直到该线程过程完成.
     * 所以必须关掉/设置成守护线程
     */
    class CacheUpdateDaemon extends Thread {
        public CacheUpdateDaemon() {
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(ServiceConfig.REGISTRY_UPDATES_CACHE_DAEMON_INTERNAL);
                    long timestamp = System.currentTimeMillis();

                    synchronized (recentlyChangedQueue) {
                        while (recentlyChangedQueue.size() > 0) {
                            InstanceInfoChangedHolder infoChangedHolder = recentlyChangedQueue.peek();
                            // 如果没有过期的cache, 就下次循环了.
                            if (Objects.isNull(infoChangedHolder) ||
                                    timestamp - infoChangedHolder.getTimestamp() < REGISTRY_UPDATES_CACHE_EXPIRE_INTERNAL) {
                                continue;
                            }
                            // 把过期的缓存干掉
                            recentlyChangedQueue.poll();
                        }
                    }
                } catch (InterruptedException e) {
                    log.info("CacheUpdateDaemon was interrupted, exit");
                    return;
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }
}
