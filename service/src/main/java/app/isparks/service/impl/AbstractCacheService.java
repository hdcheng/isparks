package app.isparks.service.impl;


import app.isparks.core.dao.cache.AbstractCacheStore;
import app.isparks.core.service.ICacheService;
import app.isparks.core.service.support.BaseService;

/**
 * @author chenghd
 */
public abstract class AbstractCacheService extends BaseService implements ICacheService {

    private AbstractCacheStore<String,Object> cacheStore;
    

    public void setCacheStore(AbstractCacheStore<String,Object> cacheStore){
        if(cacheStore == null){
            return;
        }
        this.cacheStore = cacheStore;
    }

    public AbstractCacheStore getCacheStore() {
        return cacheStore;
    }

}
