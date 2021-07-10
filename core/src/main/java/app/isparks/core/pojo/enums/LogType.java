package app.isparks.core.pojo.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    static final String prefix = "[";

    static final String suffix = "]";

    static final String separation = ",";

    private String type;

    private Integer code;

    LogType(Integer code,String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    /**
     * 返回类型字符串
     */
    public static String types(LogType ... types){
        if(types == null || types.length == 0){
            return prefix + suffix;
        }

        StringBuilder s = new StringBuilder(prefix);

        List<LogType> typeList = Arrays.asList(types);

        for(LogType type : LogType.values()){
            if(typeList.contains(type)){
                s.append(type.getType());
                s.append(separation);
            }
        }

        s.deleteCharAt(s.length()-1);
        s.append(suffix);
        return s.toString();
    }

    public static List<LogType> types(String typesStr){

        if(typesStr == null || typesStr.isEmpty()){
            return new ArrayList<>();
        }

        typesStr = typesStr.replaceAll("[\\[\\] ]{1}","");
        String[] types = typesStr.split(separation);
        List<LogType> result = new ArrayList<>(types.length);

        for(String type : types){
            for(LogType logType : LogType.values()){
                if(logType.type.equals(type)){
                    result.add(logType);
                    break;
                }
            }
        }
        return result;
    }

}
