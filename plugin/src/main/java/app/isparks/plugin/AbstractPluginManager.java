package app.isparks.plugin;

import app.isparks.core.plugin.IPlugin;
import app.isparks.core.plugin.PluginManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： chenghd
 * @date： 2021/3/20
 */
public abstract class AbstractPluginManager implements PluginManager {

    private final Map<String, IPlugin> plugins = new ConcurrentHashMap<>();

    private int size = 0; // 插件数量

    /**
     * 统计已经注册插件的数量
     */
    @Override
    public int size(){
        return size;
    }

    /**
     * 安装插件
     */
    @Override
    public IPlugin install(String name,final IPlugin plugin){
        this.plugins.put(name,plugin);
        size ++;
        return plugin;
    }

    /**
     * 安装插件并激活
     */
    public void install(String name,final IPlugin plugin,Object ... args){
        install(name,plugin).activate(args);
    }

    /**
     * 卸载插件
     */
    @Override
    public void uninstall(String name){
        IPlugin plugin = this.plugins.remove(name);
        if(plugin != null){
            size --;
            plugin.deactivate();
        }
    }

}
