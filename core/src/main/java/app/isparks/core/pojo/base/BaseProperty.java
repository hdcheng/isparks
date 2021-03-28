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
    public void setProperties(Map<String,Object> properties){
        this.properties = properties;
    }

    /**
     * 设置属性，如果key值存在则覆盖
     */
    public void setProperty(String key,Object value){
        this.properties.put(key,value);
    }

    /**
     * 设置属性，如果key值存在则失败
     */
    public void setPropertyIfAbsent(String key,Object value){
        this.properties.putIfAbsent(key,value);
    }

    /**
     * 获取属性值
     */
    public Optional<Object> getProperty(String key){
        return Optional.ofNullable(this.properties.get(key));
    }


}
