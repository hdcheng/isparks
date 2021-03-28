package app.isparks.dao.config.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * data source factory
 *
 * @author chenghd
 * @date 2020/7/30
 */
public class HikariDataSourceBuilder {

    public static HikariDataSource create(HikariDataSourceInfo info) {
        HikariConfig config = new HikariConfig();
        config.setUsername(info.getUserName());
        config.setPassword(info.getPassword());
        config.setJdbcUrl(info.getUrl());
        config.setDriverClassName(info.getDriverClassName());

        config.setPoolName(info.getPoolName());
        config.setMinimumIdle(info.getMininumIdle());
        config.setIdleTimeout(info.getIdleTimeout());
        config.setMaximumPoolSize(info.getMaximumPoolSize());
        config.setAutoCommit(info.getAutoCommit());
        config.setMaxLifetime(info.getMaxLifeTime());
        config.setConnectionTimeout(info.getConnectionTimeout());
        config.setConnectionTestQuery(info.getConnectionTestQuery());

        return new HikariDataSource(config);
    }

}
