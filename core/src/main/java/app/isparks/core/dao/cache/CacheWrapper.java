package app.isparks.core.dao.cache;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 缓存对象包装类
 *
 * @author chenghd
 * @date 2020/8/14
 */
@Data
@ToString
public class CacheWrapper<V> implements Serializable {


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
    private long createAt;

}
