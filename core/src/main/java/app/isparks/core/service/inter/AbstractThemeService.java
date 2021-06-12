package app.isparks.core.service.inter;

import app.isparks.core.service.IThemeService;
import app.isparks.core.service.support.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractThemeService extends BaseService implements IThemeService {

    private Logger log = LoggerFactory.getLogger(AbstractThemeService.class);

    // 内置的主题
    private final Map<String,String> INTERNAL_THEMES_MAP = new ConcurrentHashMap<>();

    // 激活的主题
    protected String ACTIVE_THEME_ID ;

    protected String ACTIVE_THEME_URI ;

    private boolean IS_INTERNAL_THEME = true;

    public AbstractThemeService(){
        INTERNAL_THEMES_MAP.put("meet","web/meet");
    }

    @Override
    public String resolveTheme(String filePath) {

        if(ACTIVE_THEME_ID == null || "".equals(ACTIVE_THEME_ID)){
            return filePath;
        }

        if(IS_INTERNAL_THEME){
            return filePath.replaceFirst("web", ACTIVE_THEME_URI);
        }else {
            return resolveCustomizeTheme(filePath);
        }
    }



    @Override
    public synchronized void active(String themeId) {

        if( themeId == null || themeId.equals(ACTIVE_THEME_ID)){return;}

        // ACTIVE_THEME_ID == null 为初始化操作。
        if(ACTIVE_THEME_ID != null){
            // todo:异步操作
            updateThemeConfig(themeId);
        }

        IS_INTERNAL_THEME = INTERNAL_THEMES_MAP.containsKey(themeId);
        ACTIVE_THEME_ID = "";
        ACTIVE_THEME_URI = "";

        if(IS_INTERNAL_THEME){
            // 更新成内部主题
            INTERNAL_THEMES_MAP.entrySet().forEach(entry -> {
                if(themeId.equals(entry.getKey())){
                    ACTIVE_THEME_ID = entry.getKey();
                    ACTIVE_THEME_URI = entry.getValue();
                }
            });
        }else{
            // 激活自定义主题
            activeCustomizeTheme(themeId);
        }

    }


    /**
     * 获取当前的主题 id
     */
    public String themeId(){
        return ACTIVE_THEME_ID == null ? "" : ACTIVE_THEME_ID;
    }

    public synchronized void setThemeId(String themeId){
        this.ACTIVE_THEME_ID = themeId;
    }

    @Override
    public Map<String, String> listThemes() {
        Map<String, String> themes = new HashMap<>(INTERNAL_THEMES_MAP);
        themes.putAll(customizeThemes());
        return themes;
    }

    /**
     * 更新主题id
     */
    public abstract void updateThemeConfig(String themeId);

    /**
     * 将配置信息更新到数据库
     */
    protected abstract void storeThemeConfig(String themeId);

    /**
     * 更新前
     */
    protected abstract void beforeUpdate(String themeId);

    /**
     * 激活自定义主题
     */
    protected abstract void activeCustomizeTheme(String themeId);

    /**
     * 解析自定义主题路径
     */
    protected abstract String resolveCustomizeTheme(String pathName);

    /**
     * 扫描本地自定义文件
     */
    protected abstract Map<String, String> customizeThemes();



}
