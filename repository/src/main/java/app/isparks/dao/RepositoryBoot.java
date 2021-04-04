package app.isparks.dao;

import app.isparks.core.config.DBConfig;
import app.isparks.core.framework.IBoot;
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



    }
}
