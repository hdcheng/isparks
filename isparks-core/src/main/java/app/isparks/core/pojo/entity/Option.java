package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.core.pojo.enums.OptionType;

public class Option extends BaseEntity {

    /**
     * Option key
     */
    private String key;

    /**
     * option value
     */
    private String value;

    /**
     * 类型
     */
    private Integer type;

    public Option(){}

    public Option(String key,String value){
        this.key = key;
        this.value = value;
    }

    public Option(OptionType type){
        this.type = type.getCode();
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
