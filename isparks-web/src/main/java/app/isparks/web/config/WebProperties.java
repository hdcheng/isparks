package app.isparks.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * system config
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Configuration
public class WebProperties{

    /**
     * 静态资源访问路径
     */
    public final static String STATIC_REQUEST_MAP = "/static/**";

    /**
     * 域名
     */
    public static String HOST = "http://127.0.0.1:8174";


    @Value("${spring.application.name}")
    public void setAppName(String name){
        if(!"".equals(name)){
        }
    }


    /**
     * 配置文件名
     */
    public static String configFile;

    @Value("${isparks.root}")
    public void setConfigFile(String rootPath) {
        WebProperties.configFile = rootPath + File.separator + "config.yaml";
    }

    /**
     * 是否禁用 api 文档
     */
    public static boolean docAvailable;

    @Value("${isparks.doc}")
    public void setDisableDoc(String doc) {
        WebProperties.docAvailable = "able".equals(doc);
    }

    /**
     * 是否开启页面缓存（后台页面；默认true）
     */
    public static boolean cache;

    @Value("${isparks.cache}")
    public void setCache(boolean cache) {
        WebProperties.cache = cache;
    }

}
