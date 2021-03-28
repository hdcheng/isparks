package app.isparks.core.dao.cache;


import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 缓存储存接口
 *
 * @author chenghd
 * @date 2020/8/3
 */
public interface CacheStore<K, V> {

    /**
     * 从缓存中获取值
     *
     * @param k 键
     * @return 可能为 Empty
     */
    Optional<V> get(K k);

    /**
     * 存放缓存值,如果已经存在，则覆盖
     *
     * @param k        键
     * @param v        值
     * @param timeout  超时时长
     * @param timeUnit 时间单位
     */
    void put(K k, V v, long timeout, TimeUnit timeUnit);

    /**
     * 存放缓存值,如果已经存在，则覆盖。
     *
     * @param k        键
     * @param v        值
     */
    void put(K k,V v);

    /**
     * 存放缓存值,如果已经存在，覆盖
     *
     * @param k        键
     * @param v        值
     * @param timeout  超时时长
     * @param timeUnit 时间单位
     * @return 成功与否
     */
    boolean putIfAbsent(K k, V v, long timeout, TimeUnit timeUnit);

    /**
     * 删除值
     *
     * @param k
     * @return 被删除的值/可为Empty
     */
    Optional<V> delete(K k);

}
