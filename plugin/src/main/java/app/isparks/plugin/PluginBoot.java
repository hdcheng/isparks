package app.isparks.plugin;

import app.isparks.core.framework.IBoot;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.core.plugin.IPlugin;
import app.isparks.core.plugin.PluginManager;
import app.isparks.core.util.IOCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： chenghd
 * @date： 2021/3/17
 */
public class PluginBoot implements IBoot {

    private Logger log = LoggerFactory.getLogger(PluginBoot.class);

    private static Map<String,IPlugin> plugins = new ConcurrentHashMap<>();

    @Override
    public void boot(Object... args) {
        IOCUtils.getBeanByClass(PluginManager.class).ifPresent(pm -> {
            ISparksApplication.instance().register(new DefaultPluginManager());
        });

        loadPlugin();
        activePlugin();
    }

    /**
     * 加载插件
     */
    public void loadPlugin(){
        ClassPathResource resource =new ClassPathResource("plugin/config/image-plugin.properties");

        try(InputStream is = resource.getInputStream()){
            Properties prop = new Properties();
            prop.load(is);
            String name = String.valueOf(prop.get("name"));
            String classPath = String.valueOf(prop.getProperty("class"));

            Class aClass = Class.forName(classPath);
            IPlugin plugin  = (IPlugin) aClass.newInstance();

            plugins.put(name,plugin);

        }catch (IOException |ClassNotFoundException|InstantiationException|IllegalAccessException e){
            log.error("加载配置文件失败",e);
        }

        ClassPathResource resource1 =new ClassPathResource("plugin/config/blog-plugin.properties");
        try(InputStream is = resource1.getInputStream()){
            Properties prop = new Properties();
            prop.load(is);
            String name = String.valueOf(prop.get("name"));
            String classPath = String.valueOf(prop.getProperty("class"));

            Class aClass = Class.forName(classPath);
            IPlugin plugin  = (IPlugin) aClass.newInstance();

            plugins.put(name,plugin);

        }catch (IOException |ClassNotFoundException|InstantiationException|IllegalAccessException e){
            log.error("加载配置文件失败",e);
        }

    }


    /**
     * 激活插件
     */
    public void activePlugin(Object ... args){
        plugins.values().forEach((plugin) -> {
            plugin.activate(args);
        });
    }

}
