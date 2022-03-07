package app.isparks.core.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chenghd
 */
public abstract class AbstractCacheCleaner<K,V> implements CacheStore<K, V> {

    private Logger log = LoggerFactory.getLogger(AbstractCacheCleaner.class);

    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public AbstractCacheCleaner(){
        executor.scheduleWithFixedDelay(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            try {
                cleanExpireCache(allKeys());
            }catch (Exception e){
                log.error("Clean cache exception",e);
            }
        },0,10, TimeUnit.SECONDS);
    }

    /**
     * Get all keys
     */
    protected abstract List<K> allKeys();

    /**
     * Get CacheWrapper by key for cleaning expire cache.
     */
    protected abstract Optional<CacheWrapper<V>> getCacheWrapperByKey(K k);

    private void cleanExpireCache(List<K> keys){
        if(keys == null || keys.size() == 0){
            return;
        }
        for(K k : keys){
            getCacheWrapperByKey(k).ifPresent( wrapper -> {
                log.debug("缓存->[{}]:[{}]",k,wrapper.getData());
                if(!wrapper.isAlive()){
                    log.info("Cache [{}] has expired",k);
                    invalidate(k);
                }
            });
        }
    }

}
