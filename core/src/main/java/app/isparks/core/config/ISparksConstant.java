package app.isparks.core.config;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * isparks constant.
 * 常量类
 *
 * @author： chenghd
 * @date： 2021/1/3
 */
public final class ISparksConstant {

    // 版本
    public static final String ISPARKS_VERSION;

    // 未知版本
    public final static String UNKNOWN_VERSION = "unknown";

    // 根目录文件名
    public final static String BASE_APP_DIR_NAME = ".isparks";

    // 图片文件目录名
    public final static String IMAGE_DIR_NAME = "images";

    // 配置文件目录名
    public final static String CONFIG_DIR_NAME = "config";

    // 静态文件目录名
    public final static String RESOURCES_DIR_NAME = "resources";

    // 主题文件目录名
    public final static String THEME_DIR_NAME = "theme";

    // 模板文件目录名
    public final static String TEMPLATES_DIR_NAME = "templates";

    // 插件文件目录名
    public final static String PLUGINS_DIR_NAME = "plugins";

    //authorization
    public final static String AUTHORIZATION = "authorization";

    // Path separator
    public final static String PATH_SEPARATOR = File.separator;

    // https
    public final static String PROTOCOL_HTTPS = "https://";

    // http
    public final static String PROTOCOL_HTTP = "http://";

    // /
    public final static String URL_SEPARATOR = "/";

    // Temporary directory.
    public final static String TEMP_DIR = "tmp" + PATH_SEPARATOR + "run.isparks.app";

    // 每次启动的时候，生成一个动态的 key
    public final static String DYNAMIC_PRIVATE_KEY = "ISPARKS-DYNAMIC-" + UUID.randomUUID().toString().replace("-","").toUpperCase();

    // 永远必变的唯一静态KEY
    public final static String STATIC_PRIVATE_KEY = "ISPARKS-DYNAMIC-B73DBEB06DC74AC59FD88641AEF2E3D3";

    static {
        ISPARKS_VERSION = Optional.ofNullable(ISparksConstant.class.getPackage().getImplementationVersion()).orElse(UNKNOWN_VERSION);
    }

}
