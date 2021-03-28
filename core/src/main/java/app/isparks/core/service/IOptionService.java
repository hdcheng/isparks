package app.isparks.core.service;

import app.isparks.core.pojo.entity.Option;
import app.isparks.core.pojo.enums.PropertyEnum;
import app.isparks.core.pojo.param.OptionParam;

import java.util.Map;
import java.util.Optional;

/**
 * Option
 *
 * @author： chenghd
 * @date： 2021/1/13
 */
public interface IOptionService {


    /**
     * 保存配置
     *
     * @param param
     */
    void save(OptionParam param);


    /**
     * 保存配置，如果已存在则更新原有数据
     *
     * @param mapOptions
     */
    void saveOrUpdate(Map<String,Object> mapOptions);


    /**
     * 根据 SystemProperties 获取值，如果获取的值为空，返回 SystemProperties 的默认值
     *
     * @param property
     * @param valueType
     * @return
     */
    <V> V getByPropertyOrDefault(PropertyEnum property,Class<V> valueType);

    /**
     * 根据 Key 值获取数据
     *
     * @param key
     * @return
     */
    Optional<Option> getOptionByKey(String key);

    /**
     * 获取所有 OPTIONS
     *
     * @return options转换成的map
     */
    Map<String,Object> listOptions();

    /**
     * 是否存在此表格
     * @return true/false
     */
    boolean connectable();

}
