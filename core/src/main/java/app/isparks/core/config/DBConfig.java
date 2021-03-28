package app.isparks.core.config;

import app.isparks.core.dao.dialect.DBAction;
import app.isparks.core.dao.dialect.DefaultDialectResolveInfo;
import app.isparks.core.dao.dialect.DialectResolveInfo;
import app.isparks.core.dao.dialect.IDatabaseEnum;
import app.isparks.core.pojo.enums.PropertyEnum;
import app.isparks.core.pojo.enums.SystemProperties;
import app.isparks.core.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * 数据库属性配置文件
 *
 * @author chenghd
 * @date 2020/10/5
 */

public class DBConfig {

    private static String url ;

    private static String ip ;

    private static String port ;

    private static String driver ;

    private static String userName ;

    private static String password ;

    private static IDatabaseEnum database ;

    private static String dbPrefix = "i";

    private final static String dbSuffix = "sparks";

    /**
     * 检测数据库信息是否完整
     *
     * @return database information is complete
     */
    public static synchronized boolean isComplete(){

        if(StringUtils.hasEmpty(ip,url,port,driver,userName,password,dbPrefix)){
            return false;
        }

        if (database == null){
            return false;
        }

        return true;
    }

    /**
     * 更新配置信息
     */
    public static synchronized void update(IDatabaseEnum database,String ip,String port,String userName,String password,String dbPrefix){
        if(database == null || StringUtils.hasEmpty(ip,port,userName)){
            return;
        }

        DBConfig.ip = ip;
        DBConfig.port = port;
        DBConfig.userName = userName;
        DBConfig.password = password;
        DBConfig.dbPrefix = StringUtils.isEmpty(dbPrefix) ? "i" : dbPrefix;
        DBConfig.database = database;

        String dbName = (StringUtils.isEmpty(dbPrefix) ? "i" : dbPrefix) + dbSuffix;

        //DBAction action = database.getDBAction();
        DefaultDialectResolveInfo info = new DefaultDialectResolveInfo(database.driverClass(),ip,port,dbName,userName,password);
        DBAction action = database.resolveDialect(info);

        DBConfig.url = action.getUrl();
        DBConfig.driver = database.driverClass();
    }

    /**
     * 更新配置信息
     */
    public static synchronized void update(IDatabaseEnum database,String ip ,String port,String userName,String password){
        update(database,ip,port,userName,password,"i");
    }

    /**
     * 更新配置信息
     */
    public static synchronized void update(IDatabaseEnum database,Map<String,Object> config){
        String ip = String.valueOf(config.get(DatabaseProperties.DATABASE_IP.getKey()));
        String port = String.valueOf(config.get(DatabaseProperties.DATABASE_PORT.getKey()));
        String userName = String.valueOf(config.get(DatabaseProperties.DATABASE_USERNAME.getKey()));
        String password = String.valueOf(config.get(DatabaseProperties.DATABASE_PASSWORD.getKey()));
        String dbPrefix = String.valueOf(config.get(DatabaseProperties.DATABASE_NAME_PREFIX.getKey()));
        update(database,ip,port,userName,password,dbPrefix);
    }

    /**
     * 检测数据库信息是否完整
     *
     * @return database information is complete
     */
    public synchronized static Optional available(){
        return isComplete() ? Optional.of(Boolean.TRUE) : Optional.empty();
    }

    /**
     * 获取数据库名
     *
     * @return database name
     */
    public synchronized static String getDBName() {
        return dbPrefix + dbSuffix;
    }

    /**
     * 根据给的数据库前缀获取数据库表名
     *
     * @param dbPrefix
     * @return database name
     */
    public static String getDBNameByPrefix(String dbPrefix){
        return dbPrefix + dbSuffix;
    }

    /**
     * 获取数据库名前缀
     *
     * @return database name prefix
     */
    public synchronized static String getDBPrefix() {
        return dbPrefix;
    }

    /**
     * 设置数据库名的前缀
     *
     * @param dbPrefix
     */
    public synchronized static void setDBPrefix(String dbPrefix) {
        DBConfig.dbPrefix = dbPrefix;
    }

    /**
     * 获取数据库链接URL
     *
     * @return
     */
    public synchronized static String getUrl() {
        return url;
    }

    /**
     * 设置数据库链接URL
     *
     * @param url
     */
    public synchronized static void setUrl(String url) {
        DBConfig.url = url;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public synchronized static String getUserName() {
        return userName;
    }

    public synchronized static void setUserName(String userName) {
        DBConfig.userName = userName;
    }

    public synchronized static String getPassword() {
        return password;
    }

    public synchronized static void setPassword(String password) {
        DBConfig.password = password;
    }

    public synchronized static String getDriver() {
        return driver;
    }

    public synchronized static void setDriver(String driver) {
        DBConfig.driver = driver;
    }

    public synchronized static IDatabaseEnum getDatabase() {
        return database;
    }

    public synchronized static void setDatabase(IDatabaseEnum database) {
        DBConfig.database = database;
        DBConfig.driver =database.driverClass();
    }

    public synchronized static String getIp() {
        return ip;
    }

    public synchronized static void setIp(String ip) {
        DBConfig.ip = ip;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        DBConfig.port = port;
    }

    public enum  DatabaseProperties implements PropertyEnum{

        DATABASE_IP("database_ip","127.0.0.1",String.class),
        DATABASE_PORT("database_port","8174",String.class),
        DATABASE_USERNAME("database_username","sa",String.class),
        DATABASE_PASSWORD("database_password","",String.class),
        DATABASE_NAME_PREFIX("database_name_prefix","",String.class);

        private final String key;
        private final String value;
        private final Class<?> valueType;

        DatabaseProperties(String key,String defaultValue,Class<?> valueType){
            this.key = key;
            this.value = defaultValue;
            this.valueType = valueType;
        }

        @Override
        public String getCode() {
            return key;
        }

        @Override
        public String getKey() {
            return getCode();
        }

        @Override
        public Class<?> getValueType() {
            return valueType;
        }

        @Override
        public String getValue() {
            return value;
        }

        public String getDefaultValue() {
            return value;
        }

    }

}
