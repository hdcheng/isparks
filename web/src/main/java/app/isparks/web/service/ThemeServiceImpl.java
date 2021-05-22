package app.isparks.web.service;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.exception.ThemeException;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.ISysService;
import app.isparks.core.service.inter.AbstractThemeService;
import app.isparks.core.util.ResourcesUtils;
import app.isparks.core.web.property.WebConstant;
import app.isparks.core.web.property.WebProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThemeServiceImpl extends AbstractThemeService {

    private final static String FILE_PREFIX = "file:";

    private final static String THEME_CONFIG_FILE_NAME = "theme.config";

    private final static String CUSTOMIZE_THEME_FILE_PATH = ISparksProperties.CUSTOMIZE_THEME_FILE_PATH + ISparksConstant.PATH_SEPARATOR;

    private final static String WEB_TEMPLATE_CLASSPATH = WebConstant.WEB_TEMPLATE_CLASSPATH+ISparksConstant.URL_SEPARATOR;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private IOptionService optionService;

    @Autowired
    private ISysService sysService;

    @Override
    protected String resolveCustomizeTheme(String filePath) {
        String themeId = themeId();
        String pathUri = FILE_PREFIX +ResourcesUtils.parseFilePathToURI(CUSTOMIZE_THEME_FILE_PATH + themeId);

        return filePath.replaceFirst(WEB_TEMPLATE_CLASSPATH,pathUri);
    }

    @Override
    protected void activeCustomizeTheme(String themeId) {

        customizeThemes().entrySet().forEach(entry -> {
            if(entry.getKey().equals(themeId)){
                ACTIVE_THEME_ID = entry.getKey();
                ACTIVE_THEME_URI = entry.getValue();
            }
        });

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
    protected void beforeUpdate(String themeId) {
        // 清空缓存
        templateEngine.getCacheManager().clearAllCaches();
    }

    @Override
    public void initTheme() {
        setThemeId(null);
        String themeId = optionService.getByPropertyOrDefault(WebProperties.WEBSITE_THEME_NAME,String.class);
        active(themeId);
    }

    private void checkThemePath(){
        String themeId = themeId();
        new File(ISparksProperties.CUSTOMIZE_THEME_FILE_PATH);
    }
    private void checkThemePath(File filePath){
        if(!filePath.exists() || !filePath.isDirectory()){
            throw new ThemeException(String.format("主题路径 {} 不存在", filePath.getName()));
        }
    }

    @Override
    protected Map<String,String> customizeThemes() {
        File[] files = new File(ISparksProperties.CUSTOMIZE_THEME_FILE_PATH).listFiles();
        Map<String,String> customizeThemes = new HashMap<>();
        Arrays.stream(files).forEach(file -> {
           if(file.isDirectory()){
               String themeid = file.getName();
               File config = new File(file,THEME_CONFIG_FILE_NAME);
               if(config.exists()){
                   customizeThemes.put(themeid,themeid);
               }
           }
        });
        return customizeThemes;
    }

}
