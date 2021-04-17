package app.isparks.core.service;

public interface IThemeService {

    // 解析主题
    String resolveTheme(String filePath);

    // 激活主题
    void active(String themeId);

}
