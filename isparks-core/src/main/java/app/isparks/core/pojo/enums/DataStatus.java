package app.isparks.core.pojo.enums;

/**
 * status 数据状态
 *
 * @author chenghd
 * @date 2020/7/24
 */
public enum DataStatus implements IEnum<Integer> {
    /**
     * 移除
     */
    REMOVE(-1),

    /**
     * 有效,正常
     */
    VALID(1),

    /**
     * 无效
     */
    INVALID(0),

    /**
     * 临时数据
     */
    TEMP(2),

    /**
     * 私密数据
     */
    PRIVATE(3);

    DataStatus(int code) {
        this.code = code;
    }
    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
    public static DataStatus getByCode(int code){
        DataStatus[] ss = DataStatus.values();
        for(DataStatus s:ss)
            if(s.getCode() == code)
                return s;
        return null;
    }

}
