package app.isparks.core.pojo.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/27
 */
public class BaseProperty {

    private Map<String,Object> properties = new HashMap<>();

    /**
     * 获取 properties map
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * 设置属性
     */
    public void addProperties(Map<String,Object> properties){
        this.properties.putAll(properties);
    }

    /**
     * 设置属性，如果key值存在则覆盖
     */
    public void addProperty(String key, Object value){
        this.properties.put(key,value);
    }

    /**
     * 设置属性，如果key值存在则失败
     */
    public void addPropertyIfAbsent(String key, Object value){
        this.properties.putIfAbsent(key,value);
    }

    /**
     * 获取属性值
     */
    public Optional<Object> obtainProperty(String key){
        return Optional.ofNullable(this.properties.get(key));
    }

    /**
     * 获取指定类型的属性值
     */
    public <T> Optional<T> obtainProperty(String key , Class<T> tClass){
        if(tClass == null){
            return Optional.empty();
        }
        Object v = this.properties.get(key);
        return v != null && tClass.isInstance(v) ? Optional.of((T)v) : Optional.empty();
    }
}
