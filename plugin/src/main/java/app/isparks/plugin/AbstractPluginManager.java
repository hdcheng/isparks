package app.isparks.plugin;

import app.isparks.plugin.pf4j.PF4JPluginManager;

import java.util.List;

public abstract class AbstractPluginManager implements IPluginManager {

    /**
     * 使用 pf4j 时间插件管理
     */
    private final static IPluginManager PM = new PF4JPluginManager();

    @Override
    public final boolean register(IPlugin plugin) {
        return PM.register(plugin);
    }

    @Override
    public final boolean startPlugin(String pluginId) {
        return PM.startPlugin(pluginId);
    }

    @Override
    public final boolean stopPlugin(String pluginId) {
        return PM.stopPlugin(pluginId);
    }


    @Override
    public final boolean reject(String pluginId) {
        IPlugin plugin = PM.plugin(pluginId);
        if(plugin != null && (plugin.getState() == PluginState.STOPPED || plugin.getState() == PluginState.DISABLE)){
            return PM.reject(pluginId);
        }
        return false;
    }

    @Override
    public List<IPlugin> plugins() {
        return PM.plugins();
    }

    @Override
    public IPlugin plugin(String id) {
        return PM.plugin(id);
    }

    /**
     * 初始化
     */
    public final synchronized void init(){
        load();
        startAll();
    }

    /**
     * 加载插件
     */
    protected synchronized void load(){

    }

    protected final synchronized void startAll(){
        List<IPlugin> plugins = plugins();
        if(plugins != null){
            for(IPlugin plugin : plugins){
                startPlugin(plugin.getId());
            }
        }
    }


}
