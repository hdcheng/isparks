package app.isparks.dao.dialect;


import app.isparks.core.dao.dialect.DBAction;
import app.isparks.core.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @author chenghd
 * @date 2020/12/10
 */
public class H2DatabaseAction extends DBAction {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final static String BASE_URL = "jdbc:h2:~/.{dbname}/h2/db";

    private final static String URL = "jdbc:h2:~/.{dbname}/h2/db";

    public H2DatabaseAction() {
        super("sql/h2-init.sql");
    }

    @Override
    public void createDB() throws RepositoryException {
        System.out.println("create h2 database.");
    }

    @Override
    public String getUrl() {
        return getTestUrl();
    }

    @Override
    public String getTestUrl() {
        return BASE_URL.replace("{dbname}", databaseName());
    }

    @Override
    public void update(String sql) {
        if(sql == null || "".equals(sql) ){return;}
        // 检测sql
        runBatchSQL(sql);
    }

    @Override
    public boolean exits() {
        return true;
    }

}
