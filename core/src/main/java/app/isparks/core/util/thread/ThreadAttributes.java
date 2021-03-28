package app.isparks.core.util.thread;

/**
 * 线程属性
 *
 * @author chenghd
 * @date 2020/8/28
 */
public interface ThreadAttributes<K, V> {

    /**
     * 获取默认属性
     */
    V getAttribute();

    /**
     * 设置默认属性值
     */
    void setAttribute(V v);

    /**
     * 获取值
     */
    V getAttribute(K k);

    /**
     * 设置值
     */
    void setAttribute(K k, V v);

}
