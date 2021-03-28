package app.isparks.dao.dialect;

import app.isparks.core.dao.dialect.DBAction;
import app.isparks.core.exception.RepositoryException;
import app.isparks.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/5
 */
public class PostgresqlAction extends DBAction {

    private Logger log = LoggerFactory.getLogger(getClass());

    // 基本链接url
    private final static String BASE_URL = "jdbc:postgresql://{ip}:{port}/postgres";

    private final static String URL = "jdbc:postgresql://{ip}:{port}/{dbName}";

    public PostgresqlAction(){
        super("sql/pg-init.sql");
    }

    @Override
    public void createDB() throws RepositoryException{
        StringBuffer sql = new StringBuffer(String.format("DROP DATABASE IF EXISTS %s;", databaseName()));
        sql.append(String.format("CREATE DATABASE %s;", databaseName()));
        runSQLByUrl(sql.toString(),getTestUrl());
    }

    @Override
    public void update(String sql) {
        if(StringUtils.isEmpty(sql)){
            return;
        }
        runBatchSQL(sql);
    }

    @Override
    public String getTestUrl() {
        return BASE_URL.replace("{ip}", ip()).replace("{port}", port());
    }

    @Override
    public String getUrl() {
        return URL.replace("{ip}", ip()).replace("{port}", port()).replace("{dbName}", databaseName());
    }
}
