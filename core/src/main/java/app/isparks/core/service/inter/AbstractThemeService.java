package app.isparks.core.service.inter;

import app.isparks.core.exception.SystemException;
import app.isparks.core.service.IThemeService;
import app.isparks.core.service.support.BaseService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractThemeService extends BaseService implements IThemeService {

    // 内置的主题
    private final Map<String,String> INTERNAL_THEMES_MAP = new ConcurrentHashMap<>();

    // 激活的主题
    private String ACTIVE_THEME_ID = "meet";

    private String ACTIVE_THEME_URI = "web/meet" ;

    private String ACTIVE_THEME_PATH = "classpath:web/meet";

    private boolean IS_INTERNAL_THEME = true;

    public AbstractThemeService(){
        INTERNAL_THEMES_MAP.put("meet","web/meet");
        INTERNAL_THEMES_MAP.put("breeze","web/breeze");
    }

    @Override
    public String resolveTheme(String filePath) {
        if(IS_INTERNAL_THEME){
            return filePath.replaceFirst("web", ACTIVE_THEME_URI);
        }else {
            return resolveCustomTheme(filePath);
        }
    }

    protected abstract String resolveCustomTheme(String pathName);

    @Override
    public synchronized void active(String themeId) {
        IS_INTERNAL_THEME = INTERNAL_THEMES_MAP.containsKey(themeId);
        if(IS_INTERNAL_THEME){
            ACTIVE_THEME_ID = themeId;
            ACTIVE_THEME_URI = INTERNAL_THEMES_MAP.get(themeId);
        }else{
            // todo:扫描本地主题文件。
            activeCustomTheme(themeId);
        }
    }

    protected abstract void activeCustomTheme(String themeId);

    /**
     * 获取当前的主题 id
     */
    public String getThemeId(){
        if(this.ACTIVE_THEME_ID == null){
            throw new SystemException("Get theme id failed.");
        }
        return this.ACTIVE_THEME_ID;
    }

}
