package app.isparks.core.dao.dialect;

import app.isparks.core.pojo.enums.IEnum;

import java.util.Optional;

/**
 * Database接口只允许Enum类型的实现
 *
 * @author： chenghd
 * @date： 2021/1/4
 */
public interface IDatabaseEnum extends IEnum<Integer> {

    Class<? extends DBAction> getDialectType();

    DBAction getDBAction();

    DBAction resolveDialect(DialectInfo info);

    String driverClass();

    /**
     * 根据 app.isparks.dao.dialect.enums.Database 和 database name 那么获取对象
     *
     * @param dc
     * @param n databaseName 数据库名
     * @param <D>
     * @return
     */
    static <D extends IEnum> Optional<D> getDatabase(Class<D> dc, String n){
        D d = null;
        try {
           d = IEnum.nameToEnum(dc,n);
        }finally {
            return Optional.ofNullable(d);
        }
    }

    @Override
    default Integer getCode() {
        return 0;
    }
}
