package app.isparks.core.dao.cache;

import java.io.Serializable;
import java.util.Objects;

/**
 * 缓存对象包装类
 *
 * @author chenghd
 * @date 2020/8/14
 */

public final class CacheWrapper<V> implements Serializable {

    public CacheWrapper(){
        this.expireRefresh = false;
        this.createAt = System.currentTimeMillis();
    }


    /**
     * cache data
     */
    private V data;

    /**
     * 过期时间
     */
    private long expireAt;

    /**
     * 创建时间
     */
    private final long createAt;

    /**
     * 每次获取的时候更新过期时间
     */
    private boolean expireRefresh;

    /**
     * 更新过期时间，但不是线程安全的。
     */
    public void updateExpire(){
        this.expireAt += (expireAt - createAt);
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(long expireAt) {
        this.expireAt = expireAt;
    }

    public long getCreateAt() {
        return createAt;
    }

    public boolean isExpireRefresh() {
        return expireRefresh;
    }

    public void setExpireRefresh(boolean expireRefresh) {
        this.expireRefresh = expireRefresh;
    }

    public boolean isAlive(){
        long curr = System.currentTimeMillis();
        return System.currentTimeMillis() < expireAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheWrapper<?> that = (CacheWrapper<?>) o;
        return expireAt == that.expireAt &&
                createAt == that.createAt &&
                expireRefresh == that.expireRefresh &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, expireAt, createAt, expireRefresh);
    }

    @Override
    public String toString() {
        return "CacheWrapper{" +
                "data=" + data +
                ", expireAt=" + expireAt +
                ", createAt=" + createAt +
                ", expireRefresh=" + expireRefresh +
                '}';
    }
}
