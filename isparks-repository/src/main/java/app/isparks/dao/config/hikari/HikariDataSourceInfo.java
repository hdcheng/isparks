package app.isparks.dao.config.hikari;

/**
 * data source info
 *
 * @author chenghd
 * @date 2020/7/30
 */
public class HikariDataSourceInfo {
    private String url;
    private String userName;
    private String password;
    private String driverClassName;

    /**
     * name
     */
    private String poolName;
    /**
     * minimum-idle: 5 #最小空闲连接数量
     */
    private Integer mininumIdle;

    /**
     * idle-timeout: 180000 #空闲连接存活最大时间，默认600000（10分钟）
     */
    private Long idleTimeout;

    /**
     * maximum-pool-size: 200 #连接池最大连接数，默认是10
     */
    private Integer maximumPoolSize;

    /**
     * auto-commit: true  #此属性控制从池返回的连接的默认自动提交行为,默认值：true
     */
    private Boolean autoCommit;

    /**
     * max-lifetime: 1800000 #此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟
     */
    private Long maxLifeTime;

    /**
     * connection-timeout: 60000 #数据库连接超时时间,默认30秒，即30000
     */
    private Long connectionTimeout;

    /**
     * connection-test-query: SELECT 1
     */
    private String connectionTestQuery;

    //设置默认值
    public HikariDataSourceInfo() {
        this.poolName = "Fence-Hikari-Pool";
        this.mininumIdle = 5;
        this.idleTimeout = 180000L;
        this.maximumPoolSize = 20;
        this.autoCommit = true;
        this.maxLifeTime = 1800000L;
        this.connectionTimeout = 60000L;
        this.connectionTestQuery = "SELECT 1";
    }

    /**
     * getter...............
     */
    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getPoolName() {
        return poolName;
    }

    public Integer getMininumIdle() {
        return mininumIdle;
    }

    public Long getIdleTimeout() {
        return idleTimeout;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public Long getMaxLifeTime() {
        return maxLifeTime;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    /**
     * setter...............
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public void setMininumIdle(Integer mininumIdle) {
        this.mininumIdle = mininumIdle;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void setMaxLifeTime(Long maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    /**
     * with...............
     */
    public HikariDataSourceInfo withUrl(String url) {
        this.url = url;
        return this;
    }

    public HikariDataSourceInfo withPassword(String password) {
        this.password = password;
        return this;
    }

    public HikariDataSourceInfo withDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public HikariDataSourceInfo withUserName(String userName) {
        this.userName = userName;
        return this;
    }


}
