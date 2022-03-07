package app.isparks.service.impl;

import app.isparks.core.config.DBConfig;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.dao.dialect.DBAction;
import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.exception.SystemException;
import app.isparks.core.pojo.enums.IEnum;
import app.isparks.core.pojo.enums.PropertyEnum;
import app.isparks.core.pojo.enums.SystemProperties;
import app.isparks.core.pojo.param.InitParam;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.ISysService;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.ISparksUtils;
import app.isparks.core.web.property.WebProperties;
import app.isparks.dao.config.IDataSourceFactory;
import app.isparks.dao.config.hikari.HikariCPDataSourceFactory;
import app.isparks.dao.dialect.enums.Database;
import app.isparks.core.service.support.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 系统服务层接口实现类
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Service
public class SysServiceImpl extends BaseService implements ISysService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private IOptionService optionService;

    private IUserService userService;

    private IDataSourceFactory dataSourceFactory;

    public SysServiceImpl(UserServiceImpl userService,OptionServiceImpl optionService ,HikariCPDataSourceFactory dataSourceFactory) {
        notNull(userService,"UserServiceImpl must not be null");
        notNull(optionService,"OptionServiceImpl must not be null");
        notNull(dataSourceFactory,"HikariCPDataSourceFactory must not be null");

        this.userService = userService;
        this.dataSourceFactory = dataSourceFactory;
        this.optionService = optionService;
    }


    @Override
    public void init(InitParam params) {
        Assert.notNull(params,"init param must not be null.");

    }

    @Override
    public boolean initDB() {

        if(DBConfig.getDatabase() == null){
            throw new SystemException("database initialize fail , because the database type do not set.");
        }

        Database database = (Database) DBConfig.getDatabase();

        DBAction action = database.getDBAction();

        action.init();

        return true;
    }

    @Override
    public boolean switchDB() {
        return switchDB(DBConfig.getDatabase().toString());
    }

    @Override
    public boolean switchDB(String newDBType) {
        notNull(newDBType,"new database type must not be null");

        Database newDB ,oldDB;

        try {
            newDB = IEnum.nameToEnum(Database.class,newDBType);

            String oldDBType = optionService.getByPropertyOrDefault(SystemProperties.DATABASE_TYPE,String.class);

            oldDB = IEnum.nameToEnum(Database.class,oldDBType);

        }catch (InvalidValueException e){
            log.warn("数据库类型不支持",e);
            return false;
        }


        if (oldDB == newDB){
            log.warn("数据库类型相同 不需要切换");
            return false;
        }

        // 数据库连接池切换链接
        dataSourceFactory.reload(DBConfig.getUserName(), DBConfig.getPassword(), DBConfig.getUrl(), DBConfig.getDatabase());

        // 是否要初始化数据库表格结构
        if(!optionService.connectable()){
            initDB();
        }

        return true;
    }

    /**
     * 根据数据库名切换使用的数据库
     * @param databaseName
     * @return
     */
    private boolean switchInUseDatabaseByName(String databaseName){
        Database inUseDatabase = (Database) DBConfig.getDatabase();
        if(inUseDatabase.toString().equals(databaseName.toUpperCase())){
            return false;
        }

        Database[] dbs = Database.values();

        for(Database db : dbs){
            if(db.toString().equals(databaseName.toUpperCase())){
                DBConfig.setDatabase(db);
            }
        }
        return false;
    }

    @Override
    public boolean isInstalled() {

        if(!checkConfigFile()){
            return false;
        }
        return true;
    }

    @Override
    public void createConfig(Map<String,Object> config,boolean cover) {
        File file = new File(ISparksProperties.CONFIG_FILE);

        // 不覆盖原有的数据
        if(file.exists() && !cover){
            return;
        }

        ISparksUtils.saveYaml(config,file);
    }

    @Override
    public void syncConfig() {
        File file = new File(ISparksProperties.CONFIG_FILE);

        if (!file.exists()){
            return;
        }

        Map<String,Object> config = ISparksUtils.readYaml(Map.class,file).orElse(new HashMap());

        // 获取配置文件中的数据库配置
        String type = (String)config.getOrDefault(SystemProperties.DATABASE_TYPE.getKey(),SystemProperties.DATABASE_TYPE.getValue());
        Database database = IEnum.nameToEnum(Database.class,type);
        Database oldDatabase = (Database) DBConfig.getDatabase();
        if(database != oldDatabase){
            DBConfig.update(database,config);
        }

        DBAction action = DBConfig.getDatabase().getDBAction();

        if (action.exist()){
            if(!action.trySQL("SELECT COUNT(1) FROM option").isPresent()){
                action.init();
            }
        }else{
            action.init();
        }

        // 切换数据库链接类型
        if(database != oldDatabase){
            switchDB(type);
        }

        config.remove(DBConfig.DatabaseProperties.DATABASE_USERNAME.getKey());
        config.remove(DBConfig.DatabaseProperties.DATABASE_PASSWORD.getKey());
        config.remove(DBConfig.DatabaseProperties.DATABASE_NAME_PREFIX.getKey());
        config.remove(DBConfig.DatabaseProperties.DATABASE_IP.getKey());
        config.remove(DBConfig.DatabaseProperties.DATABASE_PORT.getKey());

        // 数据库类型
        optionService.saveOrUpdate(config);

    }

    @Override
    public void syncToConfigFile() {
        File file = new File(ISparksProperties.CONFIG_FILE);
        Map<String,Object> config = ISparksUtils.readYaml(Map.class,file).orElse(new HashMap());

        // 网站设置

        // web title
        String webTitle = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_TITLE,String.class);
        config.put(WebProperties.WEBSITE_TITLE.getKey(),webTitle);

        // web description
        String webDes = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_DESCRIPTION,String.class);
        config.put(WebProperties.WEBSITE_DESCRIPTION.getKey(),webDes);

        // web domain
        String webdomain = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_URL,String.class);
        config.put(WebProperties.WEBSITE_URL.getKey(), webdomain);

        // web logo
        String logo = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_LOGO,String.class);
        config.put(WebProperties.WEBSITE_LOGO.getKey(), logo);

        // web logo text
        String logoText = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_LOGO_TEXT,String.class);
        config.put(WebProperties.WEBSITE_LOGO_TEXT.getKey(),logoText);

        // web favicon
        String favicon = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_FAVICON,String.class);
        config.put(WebProperties.WEBSITE_FAVICON.getKey(), favicon);

        // web footer info
        String webFooterInfo = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_FOOTER_INFO,String.class);
        config.put(WebProperties.WEBSITE_FOOTER_INFO.getKey(), webFooterInfo);

        // copy
        String copy = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_FOOTER_COPY,String.class);
        config.put(WebProperties.WEBSITE_FOOTER_COPY.getKey(), copy);

        // theme
        String themeId = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_THEME_NAME,String.class);
        config.put(WebProperties.WEBSITE_THEME_NAME.getKey(),themeId);

        ISparksUtils.saveYaml(config,file);
    }

    @Override
    public boolean checkConfigFile() {

        if(!new File(ISparksProperties.CONFIG_FILE).exists()){
            log.info("system config file does not exist");
            return false;
        }

        return true;
    }

    @Override
    public void executeSQL(String sql) {
        DBConfig.getDatabase().getDBAction().update(sql);
    }

    @Override
    public boolean existTable(String tableName) {
        Optional<ResultSet> result = DBConfig.getDatabase().getDBAction().trySQL("SELECT count(1) FROM " + tableName + ";");
        return result.isPresent();
    }


    /**
     * 从配置文件中获取值，如果没有则使用默认值
     *
     * @param config
     * @param propertyEnum
     * @return
     */
    private String getConfigOrDefault(Map<String,String> config, PropertyEnum propertyEnum){

        if(propertyEnum instanceof WebProperties){
            return Optional.ofNullable(config.get(propertyEnum.getKey())).
                    orElse(((WebProperties)propertyEnum).getDefaultValue());
        }else if(propertyEnum instanceof SystemProperties){
            return Optional.ofNullable(config.get(propertyEnum.getKey())).
                    orElse(((SystemProperties)propertyEnum).getDefaultValue());
        }
        return "";
    }

}

