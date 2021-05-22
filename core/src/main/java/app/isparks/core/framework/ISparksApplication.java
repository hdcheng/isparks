package app.isparks.core.framework;


import app.isparks.core.exception.PluginException;
import app.isparks.core.exception.SystemException;
import app.isparks.core.plugin.PluginManager;
import app.isparks.core.service.ISysService;
import app.isparks.core.util.IOCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： chenghd
 * @date： 2021/3/13
 */
public class ISparksApplication implements IBoot{

    private static Logger log = LoggerFactory.getLogger(ISparksApplication.class);

    private static Map<String,IBoot> boots = new ConcurrentHashMap<>();

    private volatile static ISparksApplication application = null;

    private static PluginManager pluginManager = null;

    private ISparksApplication(){}

    public static ISparksApplication instance(){
        if(application == null){
            synchronized (ISparksApplication.class){
                if(application == null){
                    application = new ISparksApplication();
                }
            }
        }
        return application;
    }


    public static ISparksContext run(ApplicationContext context,String ... args){
        IOCUtils.applicationContext = context;

        ISysService sysService = IOCUtils.getBeanByClass(ISysService.class).orElseThrow(() -> new SystemException("can't find ISysService"));

        if(sysService.checkConfigFile()){
            // 数据库信息与配置信息同步
            sysService.syncConfig();
        }else{
            // 初始化数据库
            sysService.initDB();
        }

        instance().boot(args);

        return null;
    }


    public void register(String name,IBoot boot){
        if(boots.get(name) == null){
            boots.put(name,boot);
        }
    }

    /**
     * 注册插件管理器
     */
    public synchronized void register(PluginManager pluginManager){
        if(getPluginManager().isPresent()){
            throw new PluginException("不能重复注册插件管理器");
        }else{
            this.pluginManager = pluginManager;
        }
    }

    /**
     * 插件管理器
     */
    public synchronized Optional<PluginManager> getPluginManager(){
        return Optional.ofNullable(pluginManager);
    }

    @Override
    public void boot(Object ... args) {
        boots.values().forEach((ib)->{
            ib.boot(args);
        });
    }

}
