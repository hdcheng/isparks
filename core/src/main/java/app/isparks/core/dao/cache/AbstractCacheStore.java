package app.isparks.core.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author chenghd
 * @date 2020/8/3
 */
public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public abstract Optional<CacheWrapper<V>> getInternal(K key);

    /**
     * 缓存,如果存在则覆盖
     *
     * @param key
     * @param cacheWrapper
     */
    public abstract void putInternal(K key, CacheWrapper<V> cacheWrapper);

    /**
     * 缓存,如果不存在则添加成功
     *
     * @param key
     * @param cacheWrapper
     */
    public abstract boolean putInternalIfAbsent(K key, CacheWrapper<V> cacheWrapper);

    @Override
    public Optional<V> get(K k) {
        Assert.notNull(k, "cache key must not be null");
        return getInternal(k).map(cache -> {
            //检测缓存是否过期
            if (cache.getExpireAt() <= System.currentTimeMillis()) {
                log.warn("缓存[{}]已过期", k);
                //删除已经过期的缓存
                delete(k);
                return null;
            }
            return cache.getData();
        });
    }


    @Override
    public void put(K k, V v, long timeout, TimeUnit timeUnit) {
        putInternal(k, builderCacheWrapper(v, timeout, timeUnit));
    }

    @Override
    public void put(K k, V v) {
        putInternal(k,builderCacheWrapper(v,30,TimeUnit.MINUTES));
    }

    @Override
    public Optional<V> delete(K k) {
        return Optional.empty();
    }

    @Override
    public boolean putIfAbsent(K k, V v, long timeout, TimeUnit timeUnit) {
        return putInternalIfAbsent(k, builderCacheWrapper(v, timeout, timeUnit));
    }

    protected CacheWrapper builderCacheWrapper(V v, long timeout, TimeUnit timeUnit) {

        CacheWrapper cacheWrapper = new CacheWrapper();
        cacheWrapper.setData(v);
        cacheWrapper.setCreateAt(System.currentTimeMillis());
        long expire = System.currentTimeMillis() + timeUnit.toMillis(timeout);
        cacheWrapper.setExpireAt(expire);
        return cacheWrapper;
    }

}
