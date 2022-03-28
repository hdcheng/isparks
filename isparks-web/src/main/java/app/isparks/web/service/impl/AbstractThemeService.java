package app.isparks.web.service.impl;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.service.support.BaseService;
import app.isparks.core.util.ResourcesUtils;
import app.isparks.core.web.property.WebConstant;
import app.isparks.web.service.IThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractThemeService extends BaseService implements IThemeService {

    private Logger log = LoggerFactory.getLogger(AbstractThemeService.class);

    private final static String FILE_PREFIX = "file:";

    // 主题文件路径
    private final static String THEME_FILE_PATH = ISparksProperties.THEME_FILE_PATH + ISparksConstant.PATH_SEPARATOR;

    private final static String WEB_TEMPLATE_CLASSPATH = WebConstant.WEB_TEMPLATE_CLASSPATH + ISparksConstant.URL_SEPARATOR;

    // 主题配置文件
    private final static String THEME_CONFIG_FILE_NAME = "theme.config";

    // 激活的主题
    protected String ACTIVE_THEME_ID ;


    @Override
    public String resolveTheme(String filePath) {

        if(ACTIVE_THEME_ID == null || "".equals(ACTIVE_THEME_ID)){
            return filePath;
        }

        String themeId = themeId();
        String pathUri = FILE_PREFIX + ResourcesUtils.parseFilePathToURI(THEME_FILE_PATH + themeId);

        return filePath.replaceFirst(WEB_TEMPLATE_CLASSPATH,pathUri);

    }



    @Override
    public synchronized void active(String themeId) {

        if( themeId == null || themeId.equals(ACTIVE_THEME_ID)){return;}

        String NEW_ACTIVE_THEME_ID = null;

        if(listThemes().keySet().contains(themeId)){
            NEW_ACTIVE_THEME_ID = themeId;
        }

        if(NEW_ACTIVE_THEME_ID != null){
            ACTIVE_THEME_ID = NEW_ACTIVE_THEME_ID;
            updateThemeConfig(themeId);
        }

    }

    @Override
    public synchronized void reset() {
        this.ACTIVE_THEME_ID = "";
        updateThemeConfig("");
    }

    /**
     * 获取当前的主题 id
     */
    public final String themeId(){
        return ACTIVE_THEME_ID == null ? "" : ACTIVE_THEME_ID;
    }

    public final synchronized void setThemeId(String themeId){
        this.ACTIVE_THEME_ID = themeId;
    }

    @Override
    public final Map<String, String> listThemes() {
        File themePath = new File(ISparksProperties.THEME_FILE_PATH);
        if(!themePath.exists()){
            themePath.mkdir();
        }
        File[] files = themePath.listFiles();
        Map<String,String> customizeThemes = new HashMap<>();

        for(File file : files){
            if(file.isDirectory()){
                String themeid = file.getName();
                File config = new File(file,THEME_CONFIG_FILE_NAME);
                if(config.exists()){
                    customizeThemes.put(themeid,themeid);
                }
            }
        }
        return customizeThemes;

    }

    /**
     * 更新主题id
     */
    public abstract void updateThemeConfig(String themeId);

    /**
     * 将配置信息更新到数据库
     */
    protected abstract void storeThemeConfig(String themeId);

}
