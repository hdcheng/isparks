package app.isparks.core.config;

import java.io.File;

/**
 * isparks app properties.
 * 应用属性
 *
 * @author： chenghd
 * @date： 2021/1/3
 */
public class ISparksProperties {

    static {
        String homeBase = System.getProperty("is.home") == null ? System.getProperties().getProperty("user.home") : System.getProperty("is.home");
        USER_HOME = homeBase + File.separator + ISparksConstant.BASE_APP_DIR_NAME;
    }

    /**
     * user home
     * 用户目录
     */
    public static String USER_HOME ;

    /**
     * app home is the installation directory by default
     * 应用目录默认为安装目录
     */
    public static String APP_HOME = System.getProperty("user.dir");

    /**
     * 系统配置文件路径
     */
    public static String CONFIG_FILE = USER_HOME + ISparksConstant.PATH_SEPARATOR + ISparksConstant.CONFIG_DIR_NAME + ISparksConstant.PATH_SEPARATOR + "config.yaml";

    /**
     * 静态文件路径
     */
    public static String RESOURCES_FILE_PATH = USER_HOME + ISparksConstant.PATH_SEPARATOR + ISparksConstant.RESOURCES_DIR_NAME;

    /**
     * 插件文件路径
     */
    public static String PLUGINS_FILE_PATH = RESOURCES_FILE_PATH + ISparksConstant.PATH_SEPARATOR + ISparksConstant.PLUGINS_DIR_NAME;

    /**
     * 插件模板路径
     */
    public static String PLUGIN_TEMPLATES_PATH = PLUGINS_FILE_PATH + ISparksConstant.PATH_SEPARATOR + ISparksConstant.TEMPLATES_DIR_NAME;

}
