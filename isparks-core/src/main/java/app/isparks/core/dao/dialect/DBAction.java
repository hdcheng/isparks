package app.isparks.core.dao.dialect;


import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.exception.RepositoryException;
import app.isparks.core.util.StringUtils;

import java.sql.*;
import java.util.List;

/**
 * Database action class.
 *
 * @author： chenghd
 * @date： 2021/1/4
 */
public abstract class DBAction implements DDLAction, DMLAction{

    private static String ip;
    private static String port;
    private static String userName;
    private static String password;
    private static String driver;
    private static String dbName;
    private boolean needUpdate = true;

    /**
     * 初始化数据库
     */
    public abstract void createDB() throws RepositoryException;

    /**
     * 检测数据库版本，查看是否需要更新。
     */
    public boolean needUpdate(){
        return needUpdate;
    }

    /**
     * 获取用户表的数据库的 URL 链接
     * 如："jdbc:postgresql://127.0.0.1:5432/isparks";
     *
     * @return jdbc url
     */
    public abstract String url();

    /**
     * 获取数据库初始化后就存在的库的 URL 链接
     * 如："jdbc:postgresql://127.0.0.1:5432/postgres"
     *
     * @return jdbc url
     */
    public abstract String getTestUrl();

    /**
     * 初始化数据库操作对象
     *
     * @param info
     * @return DBAction
     */
    public final DBAction prepare(DialectInfo info){

        this.driver = info.getDriver();

        try {
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            throw new InvalidValueException("database driver %c don't found" , info.getDriver());
        }

        this.ip = info.getIp();
        this.port = info.getPort();
        this.userName = info.getUserName();
        this.password = info.getPassword();
        this.dbName = info.getDbName();

        return this;
    }

    /**
     * 检测是实例是否已经解析
     */
    protected void prepared(){}

    /**
     * 数据库初始化逻辑
     */
    public abstract void init();

    /**
     * 检测数据库是否存在
     *
     * @return boolean
     */
    public abstract boolean exist();

    /**
     * 批量执行 sql
     * @throws SQLException
     * @return  ResultSet
     */
    public abstract ResultSet executeSQL(String sql) throws SQLException;

    /**
     * 批量执行 sql
     * @param sqls 需要执行的sql
     * @throws SQLException sql 执行异常
     * @return  ResultSet
     */
    public abstract ResultSet executeSQL(List<String> sqls) throws SQLException;

    /**
     * 执行 sql
     */
    public static final ResultSet executeSQL(String sql , String url , String username , String pwd) throws SQLException{

        if(StringUtils.hasEmpty(sql,url,url,pwd)){
            throw new SQLException("database parameters is empty.");
        }

        try (Connection conn = DriverManager.getConnection(url, username, pwd)){
            Statement statement  = conn.createStatement();

            statement.execute(sql);

            ResultSet result = statement.getResultSet();

            statement.close();

            return result;
        }
    }

    /**
     * 批量执行 sql
     */
    public static final ResultSet executeSQL(List<String> sqls , String url , String username , String pwd) throws SQLException{

        if(StringUtils.hasEmpty(url,username) || sqls == null || sqls.isEmpty()){
            throw new SQLException("database parameters is empty.");
        }

        try (Connection conn = DriverManager.getConnection(url, username, pwd)){
            Statement statement  = conn.createStatement();

            for(String sql : sqls){
                statement.addBatch(sql);
            }

            statement.executeBatch();

            ResultSet result = statement.getResultSet();

            statement.close();

            return result;
        }
    }

    public final String ip() {
        return ip;
    }

    public final String port() {
        return port;
    }

    public final String username() {
        return userName;
    }

    public final String password() {
        return password;
    }

    public final String driverClass() {
        return driver;
    }

    public final String databaseName() {
        return dbName;
    }

}
