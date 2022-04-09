package app.isparks.core.dao.dialect;

/**
 * @author chenghd
 * @date 2020/12/10
 */
public abstract class DialectInfo {

    private String driver;
    private String ip;
    private String port;
    private String dbName;
    private String userName;
    private String password;

    public DialectInfo(String driver, String ip, String port, String dbName, String userName, String password){
        this.driver = driver;
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
