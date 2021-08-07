package app.isparks.core.dao.cache;


import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Cache store interface | 缓存接口
 *
 * @author chenghd
 * @date 2020/8/3
 */
public interface CacheStore<K, V> {

    /**
     * Get value from cache | 从缓存中获取值
     *
     * @return Value or Optional.empty
     */
    Optional<V> get(K k);

    /**
     * Overwrite it if key already exists | 如果 key 已经存在则覆盖
     *
     * @param timeout  超时时长
     * @param timeUnit 超时单位
     */
    void put(K k, V v , long timeout, TimeUnit timeUnit);

    /**
     * Overwrite it if key already exists | 如果 key 已经存在则覆盖
     */
    void put(K k,V v);

    /**
     * If the key does not exist, cache the value | 如果不存在则缓存键值对
     *
     * @param timeout 超时清除缓存的时长
     * @param timeUnit 超时单位
     */
    boolean putIfAbsent(K k, V v, long timeout, TimeUnit timeUnit);

    /**
     * If the key does not exist, cache the value | 如果不存在则缓存键值对
     */
    boolean putIfAbsent(K k, V v);

    /**
     * Invalidate cache by key | 根据 key 值移除缓存
     *
     * @return The value which is deleted or Optional.empty | 返回被移除的缓存或者 Optional.empty
     */
    Optional<V> invalidate(K k);

}
