package app.isparks.dao.config;

import app.isparks.core.config.DBConfig;
import app.isparks.dao.config.hikari.DefaultHikariDataSource;
import app.isparks.dao.config.hikari.HikariCPDataSourceFactory;
import app.isparks.dao.config.hikari.HikariDataSourceBuilder;
import app.isparks.dao.config.hikari.HikariDataSourceInfo;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 动态数据源配置
 *
 * @author chenghd
 * @date 2020/7/30
 */
@Configuration
@Data
public class DataSourceConfig {

    @Bean
    @Conditional({app.isparks.dao.config.DataSourceNotExistCondition.class})
    public DataSource getDataSource() {
        return getDataSource(DBConfig.getUrl(), DBConfig.getUserName(), DBConfig.getPassword(), DBConfig.getDriver());
    }

    public DataSource getDataSource(String url,String userName,String password,String driver) {

        HikariDataSourceInfo info = new HikariDataSourceInfo().
                withDriverClassName(driver).
                withUserName(userName).
                withPassword(password).
                withUrl(url);

        DefaultHikariDataSource dataSource = new DefaultHikariDataSource();
        HikariDataSource hikariDataSource = HikariDataSourceBuilder.create(info);
        dataSource.updateDataSourceMap(hikariDataSource);

        return dataSource;
    }

}
