package app.isparks.dao.config.hikari;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义 hikari data source
 *
 * @author chenghd
 * @date 2020/7/30
 */
public class DefaultHikariDataSource extends AbstractDataSource {

    private final static String KEY = "DB";

    private static Map<String, HikariDataSource> dataSourceMap = new HashMap<>();

    @Override
    public Connection getConnection() throws SQLException {
        return dataSourceMap.get(KEY).getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return dataSourceMap.get(KEY).getConnection(username, password);
    }

    private void destroy() {
        DataSource dataSource = dataSourceMap.get(KEY);
        Closeable closeable = (Closeable) dataSource;
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDataSourceMap(HikariDataSource value) {
        updateDataSourceMap(KEY, value);
    }

    private void updateDataSourceMap(String key, HikariDataSource value) {
        destroy();
        dataSourceMap.put(key, value);
    }


}
