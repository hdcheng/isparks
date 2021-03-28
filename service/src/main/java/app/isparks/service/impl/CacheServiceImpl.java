package app.isparks.service.impl;

import app.isparks.core.service.ICacheService;
import app.isparks.dao.cache.MemoryStringCacheStore;
import app.isparks.core.service.support.BaseService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author： chenghd
 * @date： 2021/3/12
 */
@Service
public class CacheServiceImpl extends BaseService implements ICacheService {


    private MemoryStringCacheStore stringCacheStore;

    public CacheServiceImpl(MemoryStringCacheStore stringCacheStore){
        this.stringCacheStore = stringCacheStore;
    }


    @Override
    public void saveString(String key, String value) {
        stringCacheStore.put(key,value);
    }

    @Override
    public void saveStringWithExpires(String key, String value, long mills) {
        notNull(key,"key must not be null.");
        notNull(value,"value must not be null.");

        stringCacheStore.put(key,value,mills, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getString(String key) {
        return stringCacheStore.get(key).orElse("");
    }
}
