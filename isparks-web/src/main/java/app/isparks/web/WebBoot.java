package app.isparks.web;

import app.addons.AddonBoot;
import app.isparks.core.framework.IBoot;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.plugin.PluginBoot;
import app.isparks.plugin.PluginManager;
import app.isparks.service.ServiceBoot;
import app.isparks.web.controller.plugin.RequestPlugin;
import app.isparks.web.service.IThemeService;
import app.isparks.core.util.IOCUtils;

public class WebBoot implements IBoot {

    public WebBoot(String ... args){
        ISparksApplication.instance()
                .register("service",new ServiceBoot(args))
                .register("plugin",new PluginBoot(args));
        PluginManager.singleton().setRequestPlugin(new RequestPlugin());

        System.out.println();

    }

    @Override
    public void boot(Object... args) {

        IOCUtils.getBeanByClass(IThemeService.class).ifPresent(themeService -> {
            themeService.initTheme();
        });

    }

}
