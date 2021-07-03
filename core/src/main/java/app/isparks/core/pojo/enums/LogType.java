package app.isparks.core.pojo.enums;

/**
 * 方法/操作 类型
 * 用于调用 api/方法 时，日志记录
 *
 * @author chenghd
 * @date 2020/7/30
 */
public enum LogType implements IEnum<Integer>{

    SYS(1,"系统管理"),
    INSERT(2,"新增数据"),
    DELETE(3,"删除数据"),
    MODIFY(4,"修改数据"),
    UNKNOWN(5,"未知操作"),
    QUERY(6,"查找数据"),
    FILE_UPLOAD(7,"文件管理:上传"),
    VISIT(100,"访问");

    private String description;
    private Integer code;

    LogType(Integer code,String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
