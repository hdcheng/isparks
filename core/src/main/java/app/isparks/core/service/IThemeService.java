package app.isparks.core.service;

import java.util.Map;

public interface IThemeService {

    /**
     * 解析主题
     */
    String resolveTheme(String filePath);

    /**
     * 激活主题
     */
    void active(String themeId);

    /**
     * 获取主题列表
     */
    Map<String,String> listThemes();

    /**
     * 初始化主题
     */
    void initTheme();
}
