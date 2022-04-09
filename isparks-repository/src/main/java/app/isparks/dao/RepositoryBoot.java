package app.isparks.dao;

import app.isparks.core.config.DBConfig;
import app.isparks.core.dao.dialect.IDatabaseEnum;
import app.isparks.core.exception.SystemException;
import app.isparks.core.framework.IBoot;
import app.isparks.core.pojo.enums.IEnum;
import app.isparks.core.service.ISysService;
import app.isparks.core.util.IOCUtils;
import app.isparks.dao.dialect.enums.Database;

/**
 * @author： chenghd
 * @date： 2021/3/15
 */
public class RepositoryBoot implements IBoot {

    public RepositoryBoot(String ... args){
        // 默认数据库配置
        DBConfig.update(Database.H2,"127.0.0.1","8082","sa","");

    }

    @Override
    public void boot(Object... args) {

        ISysService sysService = IOCUtils.getBeanByClass(ISysService.class).orElseThrow(() -> new SystemException("can't find ISysService"));

        if(sysService.checkConfigFile()){
            // 数据库信息与配置信息同步
            sysService.syncConfig();
        }else{
            // 初始化数据库
            sysService.initDB();
        }

    }

}
