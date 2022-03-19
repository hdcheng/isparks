package app.isparks.core.util;

import java.util.Map;
import java.util.Optional;

public abstract class MapUtils {

    public static <T> Optional<T> get(Map<String,T> map , String key , Class<T> tClass){
        if(map == null || map.isEmpty() || tClass == null){
            return Optional.empty();
        }
        Object v = map.get(key);
        return v != null && tClass.isInstance(v) ? Optional.of((T)v) : Optional.empty();
    }

    public static <T> T getOrDefault(Map<String,T> map , String key , T defaultValue){
        if(map == null || map.isEmpty() || defaultValue == null){
            return defaultValue;
        }
        Object v = map.get(key);
        return v != null && defaultValue.getClass().isInstance(v) ? (T)v : defaultValue;
    }

}
