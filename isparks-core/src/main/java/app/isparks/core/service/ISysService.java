package app.isparks.core.service;

import app.isparks.core.pojo.param.InitParam;

import java.util.Map;

/**
 * 系统服务层接口
 *
 * @author chenghd
 * @date 2020/7/22
 */
public interface ISysService {

    /**
     * 系统初始化
     */
    void init(InitParam params);

    /**
     * 初始化数据库
     */
    boolean initDB();

    /**
     * 切换数据库
     */
    boolean switchDB();

    /**
     * 切换数据库
     */
    boolean switchDB(String newDBType);

    /**
     * 检测是系统是否已经初始化
     *
     * @return 是/否
     */
    boolean isInstalled();

    /**
     * 创建配置文件
     *
     * @param config
     * @param cover 是否覆盖原来的配置文件
     */
    void createConfig(Map<String,Object> config,boolean cover);

    /**
     * 将本地配置文件的配置数据同步到数据库
     */
    void syncConfig();

    /**
     * 将数据库中的配置数据同步到本地配置文件
     * 与 syncConfig() 功能相反
     */
    void syncToConfigFile();

    /**
     * 检测系统配置文件
     *
     * @return boolean
     */
    boolean checkConfigFile();

    /**
     * 执行SQL语句
     */
    void executeSQL(String sql);

    /**
     * 检测表格是否存在
     */
    boolean existTable(String tableName);

}
