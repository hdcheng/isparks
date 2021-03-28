package app.isparks.core.pojo.enums;

/**
 * 系统属性列表
 *
 * @author： chenghd
 * @date： 2021/1/20
 */
public enum  SystemProperties implements PropertyEnum{

    /**
     * system is installed
     */
    IS_INSTALLED("system_is_installed","false",Boolean.class),

    /**
     * 地区语言
     * system locale
     */
    SYSTEM_LOCALE("system_local","zh",String.class),

    /**
     * 数据库类型
     */
    DATABASE_TYPE("database_type","H2",String.class),

    /**
     * 系统初始化日期
     */
    BIRTHDAY("birthday","0",Long.class)

    ;

    private final String key;
    private final String value;
    private final Class<?> valueType;


    SystemProperties(String key,String defaultValue,Class<?> valueType){
        this.key = key;
        this.value = defaultValue;
        this.valueType = valueType;
    }

    @Override
    public String getCode() {
        return key;
    }

    @Override
    public String getKey() {
        return getCode();
    }

    @Override
    public Class<?> getValueType() {
        return valueType;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getDefaultValue() {
        return value;
    }


}
