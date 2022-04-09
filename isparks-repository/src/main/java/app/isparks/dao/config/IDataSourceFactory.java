package app.isparks.dao.config;

import app.isparks.core.dao.dialect.IDatabaseEnum;
import app.isparks.dao.config.hikari.HikariDataSourceInfo;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/7
 */
public interface IDataSourceFactory {

    /**
     * 重新加载数据库连接池
     */
    void reload(String username, String password, String url, IDatabaseEnum database);

    /**
     * 获取当前链接的数据库类型
     */
    Optional<IDatabaseEnum> databaseType();
}
