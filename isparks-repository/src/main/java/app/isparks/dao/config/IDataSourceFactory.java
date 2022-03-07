package app.isparks.dao.config;

import app.isparks.core.dao.dialect.IDatabaseEnum;
import app.isparks.dao.config.hikari.HikariDataSourceInfo;

import javax.sql.DataSource;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/7
 */
public interface IDataSourceFactory {

    void reload(String username, String password, String url, IDatabaseEnum database);

}
