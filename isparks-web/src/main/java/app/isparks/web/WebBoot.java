package app.isparks.web;

import app.isparks.core.framework.IBoot;
import app.isparks.web.service.IThemeService;
import app.isparks.core.util.IOCUtils;

public class WebBoot implements IBoot {

    public WebBoot(String ... args){

    }

    @Override
    public void boot(Object... args) {

        IOCUtils.getBeanByClass(IThemeService.class).ifPresent(themeService -> {
            themeService.initTheme();
        });

    }

}