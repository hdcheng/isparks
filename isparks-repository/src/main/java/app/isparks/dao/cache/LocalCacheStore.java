package app.isparks.dao.cache;

import app.isparks.core.dao.cache.CacheWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenghd
 * @date 2020/8/14
 */
public class LocalCacheStore<K,V> extends ConfigurableCacheStore<K,V> {

    private Logger log = LoggerFactory.getLogger(LocalCacheStore.class);

    private final Map<K, CacheWrapper<V>> CACHE_CONTAINER = new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();

    public LocalCacheStore(CacheStoreBuilder<K,V> builder) {
        super(builder);
    }

    @Override
    public Optional<CacheWrapper<V>> getInternal(K key) {
        return Optional.ofNullable(CACHE_CONTAINER.get(key));
    }

    @Override
    public void putInternal(K key, CacheWrapper<V> cacheWrapper) {
        CACHE_CONTAINER.put(key, cacheWrapper);
    }

    @Override
    public boolean putInternalIfAbsent(K key, CacheWrapper<V> cacheWrapper) {
        lock.lock();
        try {
            if (CACHE_CONTAINER.containsKey(key)) {
                log.warn("Cache already exists : {}", key);
                return false;
            }
            CACHE_CONTAINER.put(key, cacheWrapper);
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected long size() {
        return CACHE_CONTAINER.size();
    }

    @Override
    public Optional<V> invalidate(K key) {
        CacheWrapper<V> result = CACHE_CONTAINER.remove(key);
        if (result != null) {
            log.debug("remove cache {}", key);
            return Optional.ofNullable(result.getData());
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected List<K> allKeys() {
        return new ArrayList<>(CACHE_CONTAINER.keySet());
    }

}
