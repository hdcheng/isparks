package app.isparks.core.service.inter;

import app.isparks.core.exception.SystemException;
import app.isparks.core.service.IThemeService;
import app.isparks.core.service.support.BaseService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractThemeService extends BaseService implements IThemeService {

    // 内置的主题
    private final Map<String,String> INTERNAL_THEMES_MAP = new ConcurrentHashMap<>();

    // 激活的主题
    //private String ACTIVE_THEME_ID = "meet";
    private String ACTIVE_THEME_ID = "breeze";

    //private String ACTIVE_THEME_URI = "web/meet" ;
    private String ACTIVE_THEME_URI = "web/breeze" ;

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
            return resolveCustomizeTheme(filePath);
        }
    }

    protected abstract String resolveCustomizeTheme(String pathName);

    @Override
    public synchronized void active(String themeId) {
        if(IS_INTERNAL_THEME){
            updateThemeId(themeId);
        }else{
            activeCustomizeTheme(themeId);
        }
    }



    protected abstract void beforeUpdate(String themeId);

    /**
     * 获取当前的主题 id
     */
    public String getThemeId(){
        if(this.ACTIVE_THEME_ID == null){
            throw new SystemException("Get theme id failed.");
        }
        return this.ACTIVE_THEME_ID;
    }

    /**
     * 更新主题id
     * @param themeId
     */
    public void updateThemeId(String themeId){

        if(ACTIVE_THEME_ID.equals(themeId)){return;}

        // 更新前钩子
        beforeUpdate(themeId);

        IS_INTERNAL_THEME = INTERNAL_THEMES_MAP.containsKey(themeId);

        if(IS_INTERNAL_THEME){
            synchronized (this) {
                ACTIVE_THEME_ID = themeId;
                ACTIVE_THEME_URI = INTERNAL_THEMES_MAP.get(themeId);
            }
        }
    }

    protected abstract void activeCustomizeTheme(String themeId);
    /**
     * 更新自定义主题
     * @param themeId
     */
    protected abstract void updateCustomizeThemeId(String themeId);

    @Override
    public Map<String, String> listThemes() {
        Map<String, String> themes = new HashMap<>(INTERNAL_THEMES_MAP);
        INTERNAL_THEMES_MAP.keySet().forEach(key -> {
            themes.put(key,key);
        });
        customizeTheme(themes);
        return themes;
    }

    /**
     * 扫描本地自定义文件
     */
    protected abstract void customizeTheme(final Map<String, String> themesMap);
}
