package app.isparks.dao.dialect;


import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.dao.dialect.DBAction;
import app.isparks.core.exception.RepositoryException;
import app.isparks.core.file.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenghd
 * @date 2020/12/10
 */
public class H2DatabaseAction extends DBAction {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static String BASE_URL ;

    private static String URL ;

    static {
        String path = FileUtils.parseToUrl(ISparksProperties.USER_HOME);
        if (path.endsWith(ISparksConstant.URL_SEPARATOR)){
            path = path.substring(0,path.length()-1);
        }
        BASE_URL = "jdbc:h2:" + path + "/h2/data";
        URL = BASE_URL;
    }

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
    public boolean exist() {
        return true;
    }

}
