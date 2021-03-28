package app.isparks.core.pojo.enums;

/**
 * 返回结果类型
 *
 * @author chenghd
 * @date 2020/7/22
 */
public enum ResultType implements IEnum<Integer>{
    SUCCESS(8101, "success"),
    FAIL(8102, "fail"),
    ERROR(8103, "error");

    ResultType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
