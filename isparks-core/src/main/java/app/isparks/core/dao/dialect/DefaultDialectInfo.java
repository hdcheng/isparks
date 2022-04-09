package app.isparks.core.dao.dialect;

/**
 * @author： chenghd
 * @date： 2021/1/4
 */
public class DefaultDialectInfo extends DialectInfo {

    public DefaultDialectInfo(String driver, String ip, String port, String dbName, String userName, String password) {
        super(driver , ip , port , dbName , userName , password);
    }

}
