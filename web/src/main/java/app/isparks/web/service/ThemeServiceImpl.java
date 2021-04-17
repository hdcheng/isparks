package app.isparks.web.service;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.exception.ThemeException;
import app.isparks.core.service.inter.AbstractThemeService;
import app.isparks.core.util.ResourcesUtils;
import app.isparks.core.web.property.WebConstant;
import app.isparks.web.config.WebProperties;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ThemeServiceImpl extends AbstractThemeService {

    private final static String FILE_PREFIX = "file:";

    private final static String CUSTOMIZE_THEME_FILE_PATH = ISparksProperties.CUSTOMIZE_THEME_FILE_PATH + ISparksConstant.PATH_SEPARATOR;

    @Override
    protected String resolveCustomTheme(String filePath) {
        String themeId = getThemeId();
        String pathUri = FILE_PREFIX +ResourcesUtils.parseFilePathToURI(CUSTOMIZE_THEME_FILE_PATH + themeId);

        return filePath.replaceFirst(WebConstant.WEB_TEMPLATE_CLASSPATH,pathUri);
    }

    @Override
    protected void activeCustomTheme(String themeId) {

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

    public static void main(String[] args) {
        String fi= new ThemeServiceImpl().resolveCustomTheme("hell");
    }

}
