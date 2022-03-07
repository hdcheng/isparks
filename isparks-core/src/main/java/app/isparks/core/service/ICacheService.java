package app.isparks.core.service;

import java.util.Optional;

/**
 *
 * @author： chenghd
 * @date： 2021/3/12
 */
public interface ICacheService {

    /**
     * 保存字符串键值对
     *
     * @param key
     * @param value
     */
    void saveString(String key,String value);


    /**
     * invalidate cache | 使缓存失效
     */
    boolean invalidate(String key);

    /**
     * 设置指定时间的缓存值
     *
     * @param key
     * @param value
     * @param mills
     */
    void saveStringWithExpires(String key,String value,long mills);

    /**
     * 从缓存中获取字符串值
     *
     * @param key
     * @return ""或者值
     */
    String getString(String key);

    /**
     * 获取指定类型的数据
     */
    <V> Optional<V> getValue(String key,Class<V> vClass);

}
