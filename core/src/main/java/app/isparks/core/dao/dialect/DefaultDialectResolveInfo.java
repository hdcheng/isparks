package app.isparks.core.dao.dialect;

import app.isparks.core.dao.dialect.DialectResolveInfo;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/4
 */
public class DefaultDialectResolveInfo extends DialectResolveInfo {

    public DefaultDialectResolveInfo(String driver, String ip,String port, String dbName, String userName, String password) {
        super(driver , ip , port , dbName , userName , password);
    }


}
