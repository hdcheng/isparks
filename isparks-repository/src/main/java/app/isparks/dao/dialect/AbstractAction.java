package app.isparks.dao.dialect;

import app.isparks.core.dao.dialect.DBAction;
import app.isparks.core.exception.RepositoryException;
import app.isparks.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractAction extends DBAction {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static String initSqlFile;

    public AbstractAction(String initSqlFile) {
        this.initSqlFile = initSqlFile;
    }

    @Override
    protected void prepared(){
        if(StringUtils.hasEmpty(ip(),port(),driverClass(), username())){
            throw new RepositoryException("未解析的数据库操作对象");
        }
    }

    @Override
    public final void init(){

        prepared();

        if(!exist()){
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
     * 创建表格结构
     *
     * @param sql
     */
    @Override
    public final void createTables(String sql) {
        if(sql == null || "".equals(sql) ){
            throw new RepositoryException("数据库初始化异常");
        }
        executeBatchSQL(sql);
    }

    protected final void executeSQL(String sql, String url){
        prepared();
        if (StringUtils.hasEmpty(sql,url)){
            log.warn("SQL/URL is empty.");
            return;
        }
        try {

            executeSQL(sql,url,username(),password());

        }catch (SQLException e){
            log.error("数据库初始化异常",e);
        }
    }

    public final Optional<ResultSet> trySQL(String sql){

        prepared();

        if (StringUtils.isEmpty(sql)){
            return Optional.empty();
        }

        try {

            return Optional.ofNullable(executeSQL(sql,url(),username(),password()));

        }catch (SQLException e){

            log.error("execute sql error.",e);

            return Optional.empty();

        }
    }

    /**
     * 执行批量 SQL
     */
    protected final void executeBatchSQL(String sql){
        prepared();

        if (StringUtils.isEmpty(sql)){
            log.warn("SQL is empty.");
            return;
        }

        try {

            String[] sqls = sql.split(";");

            executeSQL(Arrays.asList(sqls),url(),username(),password());

        }catch (SQLException e){
            log.error("数据库初始化异常",e);
        }
    }

    @Override
    public final ResultSet executeSQL(String sql) throws SQLException{
        prepared();

        if (StringUtils.isEmpty(sql)){
            throw new SQLException("sql string is empty.");
        }

        return executeSQL(sql,url(),username(),password());
    }

    @Override
    public final ResultSet executeSQL(List<String> sqls) throws SQLException {
        prepared();
        if(sqls == null || sqls.isEmpty()){
            throw new SQLException("sql string is empty.");
        }

        return executeSQL(sqls,url(),username(),password());
    }

    /**
     * 加载 SQL 文件中的 SQL 语句
     *
     * @param path string
     * @return sql file content
     */
    protected final String loadSQL(String path){

        if (StringUtils.isEmpty(path)){
            return "";
        }

        return readSQLFile(initSqlFile);
    }

    /**
     * 读取 SQL 文件中的内容
     *
     * @param path
     * @return
     */
    private final String readSQLFile(String path){

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

        }catch (IOException e){
            log.error("加载SQL文件失败",e);
        }finally {
            return sql;
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

    @Override
    public boolean exist() {
        try{
            Connection conn = DriverManager.getConnection(url(), username(), password());
            conn.close();
            return true;
        }catch (SQLException e){
            log.info("不存在此数据库{}", databaseName());
            return false;
        }
    }
}
