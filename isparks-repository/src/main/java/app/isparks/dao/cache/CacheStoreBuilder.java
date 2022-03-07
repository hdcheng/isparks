package app.isparks.dao.cache;

import app.isparks.core.dao.cache.AbstractCacheStore;

import java.util.concurrent.TimeUnit;

/**
 * @author chenghd
 */
public final class CacheStoreBuilder<K,V> {


    protected long DEFAULT_TIMEOUT;

    protected TimeUnit DEFAULT_TIMEOUT_TIMEUNIT;


    private CacheStoreBuilder(){ }

    /**
     * Create new cache builder | 创建缓存构建器
     */
    public static CacheStoreBuilder<Object,Object> newBuilder(){
        return new CacheStoreBuilder();
    }

    public CacheStoreBuilder defaultTimeout(long timeout, TimeUnit timeUnit){
        this.DEFAULT_TIMEOUT = timeout;
        this.DEFAULT_TIMEOUT_TIMEUNIT = timeUnit;
        return this;
    }

    public <K1 extends K,V1 extends V> AbstractCacheStore<K1,V1> buildLocalCache(){
        AbstractCacheStore<K1,V1> cacheStore = new LocalCacheStore(this);
        return cacheStore;
    }


}
