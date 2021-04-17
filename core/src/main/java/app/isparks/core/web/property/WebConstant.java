package app.isparks.core.web.property;

/**
 * @author： chenghd
 * @date： 2021/3/17
 */
public final class WebConstant {

    // 通配符
    public final static String WEB_URL_WILDCARD = "/**";

    // 静态文件请求路径
    public final static String STATIC_REQUEST_URL = "/static" + WEB_URL_WILDCARD;

    // admin页面模板路径
    public final static String ADMIN_TEMPLATE_PATH_NAME = "admin";

    // admin页面模板classpath路径
    public final static String ADMIN_TEMPLATE_CLASSPATH = "classpath:/" + ADMIN_TEMPLATE_PATH_NAME;

    // plugin页面模板文件classpath路径
    public final static String PLUGIN_TEMPLATE_PATH_NAME = "plugin";

    // plugin页面模板文件路径
    public final static String PLUGIN_TEMPLATE_CLASSPATH = "classpath:/" + PLUGIN_TEMPLATE_PATH_NAME;

    // web页面模板文件路径
    public final static String WEB_TEMPLATE_PATH_NAME = "web";

    // web页面模板文件classpath路径
    public final static String WEB_TEMPLATE_CLASSPATH = "classpath:/" + WEB_TEMPLATE_PATH_NAME;

    // suffix of thymeleaf template file
    public final static String THYMELEAF_FILE_SUFFIX = ".html";

    // web model page data key
    public final static String PAGE_DATA_KEY = "PageData";
}
