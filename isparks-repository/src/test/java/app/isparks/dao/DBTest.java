package app.isparks.dao;

import app.isparks.core.config.DBConfig;
import app.isparks.core.dao.dialect.DBAction;
import app.isparks.dao.dialect.enums.Database;
import org.junit.Test;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.sql.SQLException;

/**
  * xxx.
  * @author： chenghd
  * @date： 2021/1/5
*/
public class DBTest {


    @Test
    public void h2RUN() throws SQLException {
        DBConfig.setDatabase(Database.H2);
        DBAction action = Database.H2.resolveDialect(null);

        action.init();
    }

}
