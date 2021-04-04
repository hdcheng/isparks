package app.isparks.service;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.framework.IBoot;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.dao.RepositoryBoot;

import java.io.File;
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

        Object o = ISparksProperties.APP_HOME;
        ISparksApplication.instance().register("repository",new RepositoryBoot(args));
    }

    @Override
    public void boot(Object... args) {
        initDatabase();
    }

    private void initDatabase(){

    }

}
