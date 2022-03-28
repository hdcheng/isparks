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
        if(System.getProperty("is.home") == null){
            USER_HOME = System.getProperties().getProperty("user.home") + File.separator + ISparksConstant.BASE_APP_DIR_NAME;
        }else{
            String suffix = ISparksConstant.BASE_APP_DIR_NAME.replace(".","");
            USER_HOME = System.getProperty("is.home") + File.separator + suffix;
        }
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
     * 私密文件路径
     */
    public static String PRIVATE_RESOURCES_FILE_PATH = USER_HOME + ISparksConstant.PATH_SEPARATOR + ISparksConstant.PRIVATE_RESOURCES_DIR_NAME;

    /**
     * 插件文件路径
     */
    public static String PLUGINS_FILE_PATH = USER_HOME + ISparksConstant.PATH_SEPARATOR + ISparksConstant.PLUGINS_DIR_NAME;

    /**
     * markdown 文件路径
     */
    public static String MARKDOWN_FILE_PATH = RESOURCES_FILE_PATH + ISparksConstant.PATH_SEPARATOR + ISparksConstant.MARKDOWN_DIR_NAME;

    /**
     * 主题文件路径
     */
    public static String THEME_FILE_PATH = USER_HOME + ISparksConstant.PATH_SEPARATOR + ISparksConstant.THEME_DIR_NAME;

    /**
     * 主题静态文件路径
     */
    public static String THEME_STATIC_FILE_PATH = USER_HOME + ISparksConstant.PATH_SEPARATOR + ISparksConstant.THEME_STATIC_DIR_NAME;

}
