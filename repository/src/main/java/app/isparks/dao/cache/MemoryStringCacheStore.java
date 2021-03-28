package app.isparks.dao.cache;

import app.isparks.core.dao.cache.AbstractStringCacheStore;
import app.isparks.core.dao.cache.CacheWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenghd
 * @date 2020/8/14
 */
@Component
public class MemoryStringCacheStore extends AbstractStringCacheStore {

    Logger log = LoggerFactory.getLogger(getClass());

    private final static Map<String, CacheWrapper<String>> CACHE_CONTAINER = new ConcurrentHashMap<>();

    private final Timer timer;

    private final static int PERIOD = 60 * 1000;
    private final static int DELAY = 0;

    private final Lock lock = new ReentrantLock();

    public MemoryStringCacheStore() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new PeriodCacheCleaner(), DELAY, PERIOD);
    }

    @Override
    public Optional<CacheWrapper<String>> getInternal(String key) {
        Assert.hasLength(key, "cache key must not be empty");
        return Optional.ofNullable(CACHE_CONTAINER.get(key));
    }

    @Override
    public void putInternal(String key, CacheWrapper<String> cacheWrapper) {
        Assert.hasLength(key, "cache key must not be empty");
        Assert.notNull(cacheWrapper, "cache value must not be null");
        CACHE_CONTAINER.put(key, cacheWrapper);
    }

    @Override
    public boolean putInternalIfAbsent(String key, CacheWrapper<String> cacheWrapper) {
        Assert.hasLength(key, "cache key must not be empty");
        Assert.notNull(cacheWrapper, "cache value must not be null");

        //lock
        lock.lock();
        try {
            if (get(key).isPresent()) {
                log.warn("缓存中已存在{}", key);
                return false;
            }

            putInternal(key, cacheWrapper);
            return true;

        } finally {
            //unlock
            lock.unlock();
        }
    }

    @Override
    public Optional<String> delete(String key) {
        //String result = get(key).orElse(null);
        CacheWrapper<String> result = CACHE_CONTAINER.remove(key);
        if (result != null) {
            log.debug("删除缓存{}", key);
            return Optional.ofNullable(result.getData());
        } else {
            return Optional.empty();
        }
    }

    /**
     * 周期性的清理缓存清理缓存
     */
    private class PeriodCacheCleaner extends TimerTask {
        @Override
        public void run() {
            CACHE_CONTAINER.keySet().forEach(key -> {
                if (!MemoryStringCacheStore.this.get(key).isPresent()) {
                    log.warn("移除过期缓存{}", key);
                }
            });
        }
    }
}
