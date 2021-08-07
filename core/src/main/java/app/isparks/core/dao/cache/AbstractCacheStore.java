package app.isparks.core.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author chenghd
 * @date 2020/8/3
 */
public abstract class AbstractCacheStore<K, V> extends AbstractCacheCleaner<K,V> {

    private Logger log = LoggerFactory.getLogger(AbstractCacheStore.class);

    protected long DEFAULT_TIMEOUT = 1000 * 60 * 10;

    protected TimeUnit DEFAULT_TIMEOUT_TIMEUNIT = TimeUnit.MILLISECONDS;

    /**
     * The implement of get(K) | get方法的具体实现
     */
    public abstract Optional<CacheWrapper<V>> getInternal(K key);

    @Override
    protected Optional<CacheWrapper<V>> getCacheWrapperByKey(K k) {
        return getInternal(k);
    }

    /**
     * The implement of put(...) | put方法的具体实现
     */
    protected abstract void putInternal(K key, CacheWrapper<V> cacheWrapper);

    /**
     * The implement of putIfAbsent(...) | getIfAbsent 方法的具体实现
     */
    protected abstract boolean putInternalIfAbsent(K key, CacheWrapper<V> cacheWrapper);

    /**
     * The number of cache
     */
    protected abstract long size();

    /**
     * Cache is empty
     */
    protected boolean isEmpty(){
        return size() == 0 ;
    }


    @Override
    public Optional<V> get(K k) {
        Assert.notNull(k, "cache key must not be null");
        return getInternal(k).map(cache -> {
            if (!cache.isAlive()) {
                log.warn("Cache [{}] has expired", k);
                invalidate(k);
                return null;
            }else if(cache.isExpireRefresh()){
                // 更新缓存存活时间
                cache.updateExpire();
            }
            return cache.getData();
        });
    }

    public void putAll(Map<K,V> values , long timeout, TimeUnit timeUnit){
        for(Map.Entry<K,V> e : values.entrySet()){
            put(e.getKey(),e.getValue(),timeout,timeUnit);
        }
    }

    public void put(K k, V v, long timeout, TimeUnit timeUnit,boolean readUpdate) {
        putInternal(k, builderCacheWrapper(v, timeout, timeUnit,readUpdate));
    }

    @Override
    public void put(K k, V v, long timeout, TimeUnit timeUnit) {
        putInternal(k, builderCacheWrapper(v, timeout, timeUnit));
    }

    @Override
    public void put(K k, V v) {
        //putInternal(k,builderCacheWrapper(v,30,TimeUnit.MINUTES));
        put(k, v,DEFAULT_TIMEOUT,DEFAULT_TIMEOUT_TIMEUNIT);
    }

    @Override
    public boolean putIfAbsent(K k, V v, long timeout, TimeUnit timeUnit) {
        return putInternalIfAbsent(k, builderCacheWrapper(v, timeout, timeUnit));
    }

    @Override
    public boolean putIfAbsent(K k, V v) {
        return putIfAbsent(k,v,DEFAULT_TIMEOUT,DEFAULT_TIMEOUT_TIMEUNIT);
    }

    /**
     * Create cache wrapper
     */
    protected CacheWrapper builderCacheWrapper(V v, long timeout, TimeUnit timeUnit) {
        CacheWrapper cacheWrapper = new CacheWrapper();
        cacheWrapper.setData(v);
        long curr = cacheWrapper.getCreateAt();
        cacheWrapper.setExpireAt(curr + timeUnit.toMillis(timeout));
        return cacheWrapper;
    }

    protected CacheWrapper builderCacheWrapper(V v, long timeout, TimeUnit timeUnit,boolean expireUpdate) {
        CacheWrapper cacheWrapper = builderCacheWrapper(v,timeout,timeUnit);
        if(expireUpdate){
            cacheWrapper.setData(expireUpdate);
        }
        return cacheWrapper;
    }

}
