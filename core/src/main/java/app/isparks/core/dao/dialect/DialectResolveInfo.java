package app.isparks.core.dao.dialect;

import lombok.Data;

/**
 * @author chenghd
 * @date 2020/12/10
 */
@Data
public abstract class DialectResolveInfo {

    private String driver;
    private String ip;
    private String port;
    private String dbName;
    private String userName;
    private String password;

    public DialectResolveInfo(String driver,String ip,String port,String dbName,String userName,String password){
        this.driver = driver;
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    
}
