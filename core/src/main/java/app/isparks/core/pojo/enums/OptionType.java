package app.isparks.core.pojo.enums;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/13
 */
public enum OptionType implements IEnum<Integer>{
    /**
     * internal option
     */
    INTERNAL(0),

    /**
     * custom option
     */
    CUSTOM(1);

    OptionType(Integer code){
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
