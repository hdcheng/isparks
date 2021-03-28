package app.isparks.core.dao.cache;


import app.isparks.core.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * abstract string cache store
 */
public abstract class AbstractStringCacheStore extends AbstractCacheStore<String,String>{

    private Logger log = LoggerFactory.getLogger(getClass());

    protected Optional<CacheWrapper<String>> jsonToCacheWrapper(String json) {
        Assert.hasText(json, "json value must not be null");
        CacheWrapper<String> cacheWrapper = JsonUtils.toObject(json,CacheWrapper.class);
        return Optional.ofNullable(cacheWrapper);
    }

    public <T> void cacheString(String key, T value) {
        put(key, JsonUtils.toJson(value));
    }

    public <T> void cacheString( String key,  T value, long timeout,  TimeUnit timeUnit) {
        put(key,JsonUtils.toJson(value), timeout, timeUnit);
    }

    public <T> Optional<T> getValue(String key, Class<T> type) {
        Assert.notNull(type, "Type must not be null");
        return get(key).map(value -> {
            return JsonUtils.toObject(value, type);
        });
    }
}
