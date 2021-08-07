package app.isparks.dao.cache;


import app.isparks.core.dao.cache.AbstractCacheStore;

import java.util.concurrent.TimeUnit;

/**
 * @author chenghd
 */
public abstract class ConfigurableCacheStore<K,V> extends AbstractCacheStore<K,V> {

    private final CacheStoreBuilder<K,V> builder ;

    public ConfigurableCacheStore(CacheStoreBuilder<K,V> builder){
        this.builder = builder;
        if(builder.DEFAULT_TIMEOUT > 0 && builder.DEFAULT_TIMEOUT_TIMEUNIT != null){
            defaultExpire(builder.DEFAULT_TIMEOUT,builder.DEFAULT_TIMEOUT_TIMEUNIT);
        }
    }

    public ConfigurableCacheStore defaultExpire(long timeout , TimeUnit timeUnit){
        if(timeout > 0 && timeUnit != null){
            this.DEFAULT_TIMEOUT = timeout;
            this.DEFAULT_TIMEOUT_TIMEUNIT = timeUnit;
        }
        return this;
    }

}
