package app.isparks.web.service;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.exception.ThemeException;
import app.isparks.core.service.inter.AbstractThemeService;
import app.isparks.core.util.ResourcesUtils;
import app.isparks.core.web.property.WebConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

@Service
public class ThemeServiceImpl extends AbstractThemeService {

    private final static String FILE_PREFIX = "file:";

    private final static String THEME_CONFIG_FILE_NAME = "theme.config";

    private final static String CUSTOMIZE_THEME_FILE_PATH = ISparksProperties.CUSTOMIZE_THEME_FILE_PATH + ISparksConstant.PATH_SEPARATOR;

    private final static String WEB_TEMPLATE_CLASSPATH = WebConstant.WEB_TEMPLATE_CLASSPATH+ISparksConstant.URL_SEPARATOR;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    protected String resolveCustomizeTheme(String filePath) {
        String themeId = getThemeId();
        String pathUri = FILE_PREFIX +ResourcesUtils.parseFilePathToURI(CUSTOMIZE_THEME_FILE_PATH + themeId);

        return filePath.replaceFirst(WEB_TEMPLATE_CLASSPATH,pathUri);
    }

    @Override
    protected void activeCustomizeTheme(String themeId) {
        updateCustomizeThemeId(themeId);
    }

    @Override
    protected void updateCustomizeThemeId(String themeId) {
        synchronized (this){
            updateThemeId(themeId);
        }
    }

    @Override
    protected void beforeUpdate(String themeId) {
        // 清空缓存
        templateEngine.getCacheManager().clearAllCaches();
    }

    private void checkThemePath(){
        String themeId = getThemeId();
        new File(ISparksProperties.CUSTOMIZE_THEME_FILE_PATH);
    }
    private void checkThemePath(File filePath){
        if(!filePath.exists() || !filePath.isDirectory()){
            throw new ThemeException(String.format("主题路径 {} 不存在", filePath.getName()));
        }
    }

    @Override
    protected void customizeTheme(final Map<String, String> themesMap) {
        File[] files = new File(ISparksProperties.CUSTOMIZE_THEME_FILE_PATH).listFiles();
        Arrays.stream(files).forEach(file -> {
           if(file.isDirectory()){
               String themeid = file.getName();
               File config = new File(file,THEME_CONFIG_FILE_NAME);
               if(config.exists()){
                   themesMap.put(themeid,themeid);
               }
           }
        });
    }

    public static void main(String[] args) {
        String fi= new ThemeServiceImpl().resolveCustomizeTheme("hell");
    }

}
