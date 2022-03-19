package app.isparks.plugin;

import app.isparks.core.pojo.base.BaseDTO;
import app.isparks.plugin.enhance.AbstractEnhancer;
import app.isparks.plugin.enhance.IRequestEnhancer;
import app.isparks.plugin.service.IRequestPlugin;
import app.isparks.plugin.service.IServicePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 插件管理器
 */
public final class PluginManager extends AbstractPluginManager implements IServicePlugin, IRequestPlugin {

    private static Logger log = LoggerFactory.getLogger(PluginManager.class);

    private volatile static PluginManager defaultPluginManager;

    private PluginManager(){}

    /**
     * 单例
     */
    public final static PluginManager singleton(){
        if(defaultPluginManager == null){
            synchronized (AbstractPluginManager.class){
                if(defaultPluginManager == null){
                    defaultPluginManager = new PluginManager();
                }
            }
        }
        return defaultPluginManager;
    }


    private IServicePlugin servicePlugin;

    public synchronized void setServicePluginManager(IServicePlugin spm){
        if(spm != null && servicePlugin == null){
            servicePlugin = spm;
        }
    }

    private IRequestPlugin apiPlugin;

    public synchronized void setRequestPlugin(IRequestPlugin ap){
        if(ap != null && apiPlugin == null){
            apiPlugin = ap;
        }
    }

    @Override
    public <T, D extends BaseDTO> boolean addEnhancer(Class<T> tClass, AbstractEnhancer<D> enhancer) {
        if(servicePlugin == null){
            log.warn("IServicePlugin 没有注册实现类");
            return false;
        }

        return servicePlugin.addEnhancer(tClass,enhancer);
    }

    @Override
    public <T, D extends BaseDTO> boolean removeEnhancer(Class<T> tClass, AbstractEnhancer<D> enhancer) {
        if(servicePlugin == null){
            log.warn("IServicePlugin 没有注册实现类");
            return false;
        }

        return servicePlugin.removeEnhancer(tClass,enhancer);
    }

    @Override
    public boolean addRequest(String method, String path, IRequestEnhancer enhancer) {
        if(apiPlugin == null){
            log.warn("IApiPlugin 没有注册实现类");
            return false;
        }

        return apiPlugin.addRequest(method,path,enhancer);
    }

    @Override
    public boolean removeRequest(String method, String path) {
        if(apiPlugin == null){
            log.warn("IApiPlugin 没有注册实现类");
            return false;
        }

        return apiPlugin.removeRequest(method,path);
    }

}
