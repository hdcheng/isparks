package app.isparks.dao.config.hikari;

import app.isparks.core.dao.dialect.IDatabaseEnum;
import app.isparks.dao.config.IDataSourceFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * 更新数据库连接池信息
 *
 * @author chenghd
 * @date 2020/7/30
 */
@Component
public class HikariCPDataSourceFactory implements IDataSourceFactory {

    private DataSource dataSource;

    private IDatabaseEnum databaseEnum;

    public HikariCPDataSourceFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 更新数据库链接信息
     * @param username
     * @param password
     * @param url
     */
    @Override
    public void reload(String username, String password, String url, IDatabaseEnum database) {
        HikariDataSourceInfo info = new HikariDataSourceInfo().
        withDriverClassName(database.driverClass()).
        withUserName(username).
        withPassword(password).
        withUrl(url);

        this.databaseEnum = database;

        if(dataSource instanceof DefaultHikariDataSource){
            ((DefaultHikariDataSource) dataSource).updateDataSourceMap(HikariDataSourceBuilder.create(info));
        }
    }



    /**
     * 获取一个链接
     */
    public Connection getCollection() throws SQLException {

        if(dataSource instanceof DefaultHikariDataSource){
            return ((DefaultHikariDataSource)dataSource).getConnection();
        }

        return dataSource != null ? dataSource.getConnection() : null;

    }

    @Override
    public Optional<IDatabaseEnum> databaseType() {
        return Optional.ofNullable(databaseEnum);
    }
}
