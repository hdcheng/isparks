package app.isparks.service;

import app.isparks.core.exception.SystemException;
import app.isparks.core.framework.IBoot;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.core.service.ISysService;
import app.isparks.core.util.IOCUtils;
import app.isparks.dao.RepositoryBoot;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/3/15
 */
public class ServiceBoot implements IBoot {

    public ServiceBoot(){
        ISparksApplication.instance().register("repository",new RepositoryBoot());
    }

    @Override
    public void boot(Object... args) {
        initDatabase();
    }

    private void initDatabase(){

    }

}
