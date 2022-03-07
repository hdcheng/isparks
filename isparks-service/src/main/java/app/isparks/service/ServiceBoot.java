package app.isparks.service;

import app.isparks.core.framework.IBoot;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.dao.RepositoryBoot;
import app.isparks.plugin.PluginManager;
import app.isparks.service.plugin.ServicePlugin;

import java.util.Arrays;

/**
 * @author： chenghd
 * @date： 2021/3/15
 */
public class ServiceBoot implements IBoot {

    public ServiceBoot(String ... args){

        Arrays.asList(args).forEach(arg -> {

            if(arg.startsWith("is.home=")){
                System.setProperty("is.home",arg.substring(arg.indexOf("is.home=")+8) );
            }

        });

        // service 层依赖 repository 层
        ISparksApplication.instance().register("repository",new RepositoryBoot(args));

        PluginManager.singleton().setServicePluginManager(new ServicePlugin());
    }

    @Override
    public void boot(Object... args) {

        PluginManager.singleton().init();

    }

}
