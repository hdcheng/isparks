package app.isparks.service.impl;

import app.isparks.core.dao.cache.AbstractCacheStore;
import app.isparks.dao.cache.CacheStoreBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author： chenghd
 * @date： 2021/3/12
 */
@Service
public class CacheServiceImpl extends AbstractCacheService {

    private AbstractCacheStore<String,Object> cacheStore;

    public CacheServiceImpl(){
        cacheStore = CacheStoreBuilder.newBuilder().buildLocalCache();
    }


    @Override
    public void saveString(String key, String value) {
        cacheStore.put(key,value);
    }

    public boolean invalidate(String key){
        return cacheStore.invalidate(key).isPresent();
    }

    @Override
    public void saveStringWithExpires(String key, String value, long mills) {
        notEmpty(key,"key must not be null.");
        notEmpty(value,"value must not be null.");

        cacheStore.put(key,value,mills, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getString(String key) {
        return String.valueOf(cacheStore.get(key).orElse(""));
    }

    @Override
    public <V> Optional<V> getValue(String key, Class<V> vClass) {
        notNull(vClass , "class must not be null");

        Optional<Object> res = cacheStore.get(key);
        if(res.isPresent() || vClass.isInstance(res.get())){
            return Optional.ofNullable(vClass.cast(res.get()));
        }
        return Optional.empty();
    }
}
