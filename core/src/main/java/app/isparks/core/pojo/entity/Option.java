package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.core.pojo.enums.OptionType;
import lombok.Data;

@Data
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

}
