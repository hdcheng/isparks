package app.isparks.service;

import app.isparks.core.config.ISparksProperties;
import app.isparks.core.framework.IBoot;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.IThemeService;
import app.isparks.core.util.IOCUtils;
import app.isparks.core.web.property.WebProperties;
import app.isparks.dao.RepositoryBoot;

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

        IOCUtils.getBeanByClass(IThemeService.class).ifPresent(themeService -> {
            themeService.initTheme();
        });

    }

    private void initDatabase(){

    }

}
