package app.isparks.web.service.impl;

import app.isparks.core.service.IOptionService;
import app.isparks.core.service.ISysService;
import app.isparks.core.web.property.WebProperties;
import app.isparks.service.impl.OptionServiceImpl;
import app.isparks.service.impl.SysServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ThemeServiceImpl extends AbstractThemeService {

    private IOptionService optionService;

    private ISysService sysService;

    public ThemeServiceImpl(OptionServiceImpl optionService, SysServiceImpl sysService){
        this.optionService = optionService;
        this.sysService = sysService;
    }

    @Override
    public void updateThemeConfig(String themeId) {
        // 1.储存数据库配置
        storeThemeConfig(themeId);
        // 2.同步到本地配置文件
        sysService.syncToConfigFile();
    }

    @Override
    protected void storeThemeConfig(String themeId) {
        Map<String,Object> config = new HashMap<>();
        config.put(WebProperties.WEBSITE_THEME_NAME.getKey(), themeId);
        optionService.saveOrUpdate(config);
    }

    @Override
    public void initTheme() {
        setThemeId(null);
        String themeId = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_THEME_NAME,String.class);
        active(themeId);
    }


}
