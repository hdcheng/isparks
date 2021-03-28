package app.isparks.core.dao.dialect;


import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.exception.RepositoryException;
import app.isparks.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.print.DocFlavor;
import java.io.*;
import java.sql.*;
import java.util.Optional;

/**
 * Database action class.
 *
 * @author： chenghd
 * @date： 2021/1/4
 */
public abstract class DBAction implements DDLAction, DMLAction{

    private Logger log = LoggerFactory.getLogger(getClass());

    private static String initSqlFile;
    private static String ip;
    private static String port;
    private static String userName;
    private static String password;
    private static String driver;
    private static String dbName;
    private boolean needUpdate = true;

    public DBAction(String initSqlFile){
        this.initSqlFile = initSqlFile;
    }


    /**
     * 初始化数据库
     */
    public abstract void createDB() throws RepositoryException;

    /**
     * 检测数据库版本，查看是否需要更新。
     *
     * @return boolean
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
    public abstract String getUrl();

    /**
     * 获取数据库初始化后就存在的库的 URL 链接
     * 如："jdbc:postgresql://127.0.0.1:5432/postgres"
     *
     * @return jdbc url
     */
    public abstract String getTestUrl();

    /**
     * 解析数据库操作对象
     *
     * @param info
     * @return DBAction
     */
    public DBAction resolve(DialectResolveInfo info){

        this.driver = info.getDriver();

        try {
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            throw new InvalidValueException("数据库驱动 %c 不存在",info.getDriver());
        }

        this.ip = info.getIp();
        this.port = info.getPort();
        this.userName = info.getUserName();
        this.password = info.getPassword();
        this.dbName = info.getDbName();

        return this;
    }

    /**
     * 解析数据库操作对象
     *
     * @param info
     * @param cover 是否覆盖原有数据
     * @return DBAction
     */
    public DBAction resolve(DialectResolveInfo info,boolean cover){
        this.needUpdate = cover;
        return resolve(info);
    }

    /**
     * 数据库初始化逻辑
     */
    public void init(){

        isResolved();

        if(!exits()){
            try {
                createDB();
            }catch (RepositoryException e){
                log.error("创建数据库失败",e);
                return;
            }
        }

        if(needUpdate()){
            String createSql = loadSQL(initSqlFile);
            beforeInit(createSql);
            createTables(createSql);
        }

    }


    /**
     * 检测数据库是否存在
     *
     * @return boolean
     */
    public boolean exits() {

        try{
            Connection conn = DriverManager.getConnection(getUrl(), userName(), userPwd());
            conn.close();
            return true;
        }catch (SQLException e){
            log.info("不存在此数据库{}", databaseName());
            return false;
        }
    }

    /**
     * 钩子程序 用于检测是SQL语句是否有问题
     *
     * @param sql string
     * @return sql string
     */
    public String beforeInit(String sql){
        return sql;
    }

    /**
     * 加载 SQL 文件中的 SQL 语句
     *
     * @param path string
     * @return sql file content
     */
    protected String loadSQL(String path){

        if (StringUtils.isEmpty(path)){
            return "";
        }

        return readSQLFile(initSqlFile);
    }

    /**
     * 创建表格结构
     *
     * @param sql
     */
    @Override
    public void createTables(String sql) {
        if(sql == null || "".equals(sql) ){
            throw new RepositoryException("数据库初始化异常");
        }
        // 检测sql
        runBatchSQL(sql);
    }

    /**
     * 执行批量 SQL
     *
     * @param sql string
     */
    protected void runBatchSQL(String sql){
        isResolved();
        if (StringUtils.isEmpty(sql)){
            log.warn("SQL is empty.");
            return;
        }

        try(Connection conn = DriverManager.getConnection(getUrl(), userName(), userPwd())) {

            Statement statement  = conn.createStatement();

            String[] sqls = sql.split(";");

            for(String s : sqls){
                statement.addBatch(s);
            }

            statement.executeBatch();
            statement.close();
            statement = null;
        }catch (SQLException e){
            log.error("数据库初始化异常",e);
        }
    }
    protected void runBatchSQLByUrl(String sql,String url){
        isResolved();
        if (StringUtils.isEmpty(sql)){
            log.warn("SQL/URL is empty.");
            return;
        }

        try(Connection conn = DriverManager.getConnection(url, userName(), userPwd())) {

            Statement statement  = conn.createStatement();

            String[] sqls = sql.split(";");

            for(String s : sqls){
                statement.addBatch(s);
            }

            statement.executeBatch();
            statement.close();
            statement = null;
        }catch (SQLException e){
            log.error("数据库初始化异常",e);
        }

    }

    /**
     * 执行单条语句
     *
     * @param sql
     */
    protected void runSQL(String sql){
        isResolved();
        if (StringUtils.isEmpty(sql)){
            log.warn("SQL is empty.");
            return;
        }
        try(Connection conn = DriverManager.getConnection(getUrl(), userName(), userPwd())) {

            Statement statement  = conn.createStatement();

            statement.execute(sql);

            statement.close();
            statement = null;
        }catch (SQLException e){
            log.error("数据库初始化异常",e);
        }
    }


    protected void runSQLByUrl(String sql,String url){
        isResolved();
        if (StringUtils.hasEmpty(sql,url)){
            log.warn("SQL/URL is empty.");
            return;
        }
        try(Connection conn = DriverManager.getConnection(url, userName(), userPwd())) {

            Statement statement  = conn.createStatement();

            statement.execute(sql);

            statement.close();
            statement = null;
        }catch (SQLException e){
            log.error("数据库初始化异常",e);
        }
    }

    /**
     * 尝试执行sql，出现异常也不会抛出异常，只会返回 Optional.empty() 。
     *
     * @param sql
     * @return
     */
    public Optional<ResultSet> trySQL(String sql){

        isResolved();

        if (StringUtils.isEmpty(sql)){
            return Optional.empty();
        }

        try(Connection conn = DriverManager.getConnection(getUrl(), userName(), userPwd())) {

            Statement statement  = conn.createStatement();

            statement.execute(sql);

            ResultSet result = statement.getResultSet();

            statement.close();
            statement = null;

            return Optional.ofNullable(result);

        }catch (SQLException e){
            return Optional.empty();
        }
    }

    /**
     * 读取 SQL 文件中的内容
     *
     * @param path
     * @return
     */
    private String readSQLFile(String path){

        String sql = "";
        try(InputStream is = new ClassPathResource(path).getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuffer buf = new StringBuffer();
            String line ;
            while ( (line = reader.readLine()) != null ){

                // 过滤无用的数据
                if(line.startsWith("-- ") || line.startsWith("#") || "".equals(line)){
                    continue;
                }

                buf.append(line);
            }
            sql = buf.toString();

            reader.close();
            reader = null;

        }catch (IOException e){
            log.error("加载SQL文件失败",e);
        }finally {
            return sql;
        }
    }


    /**
     * 检测是实例是否已经解析
     */
    protected void isResolved(){
        if(StringUtils.hasEmpty(ip,port,driver,userName)){
            throw new RepositoryException("未解析的数据库操作对象");
        }
    }

    public String ip() {
        return ip;
    }

    public String port() {
        return port;
    }

    public String userName() {
        return userName;
    }

    public String userPwd() {
        return password;
    }

    public String driverClass() {
        return driver;
    }

    public String databaseName() {
        return dbName;
    }

}
