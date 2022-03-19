package app.isparks.web;

import app.isparks.core.framework.IBoot;
import app.isparks.plugin.PluginManager;
import app.isparks.web.controller.plugin.RequestPlugin;
import app.isparks.web.service.IThemeService;
import app.isparks.core.util.IOCUtils;

public class WebBoot implements IBoot {

    public WebBoot(String ... args){

        PluginManager.singleton().setRequestPlugin(new RequestPlugin());

    }

    @Override
    public void boot(Object... args) {

        IOCUtils.getBeanByClass(IThemeService.class).ifPresent(themeService -> {
            themeService.initTheme();
        });

    }

}
