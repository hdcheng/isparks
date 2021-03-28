package app.isparks.core.service;

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

}
