package app.isparks.plugin.pf4j;


import app.isparks.core.config.ISparksProperties;
import app.isparks.plugin.IPlugin;
import app.isparks.plugin.IPluginManager;
import org.pf4j.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PF4JPluginManager implements IPluginManager {

    private final DefaultPluginManager pluginManager;

    public PF4JPluginManager(){
        File files = new File(ISparksProperties.PLUGINS_FILE_PATH);
        pluginManager = new DefaultPluginManager(files.toPath()){
            @Override
            protected PluginDescriptorFinder createPluginDescriptorFinder() {
                return new PropertiesPluginDescriptorFinder();
            }
        };
        pluginManager.loadPlugins();
    }


    @Override
    public List<IPlugin> plugins() {
        List<IPlugin> plugins = new ArrayList<>();
        pluginManager.getPlugins().forEach(plugin ->{
            plugins.add(new PF4JPlugin(plugin));
        });
        return plugins;
    }

    @Override
    public IPlugin plugin(String id) {
        PluginWrapper wrapper = pluginManager.getPlugin(id);
        return wrapper == null ? null : new PF4JPlugin(wrapper);
    }

    @Override
    public boolean register(IPlugin plugin) {
        // TODO: 实现这里
        return false;
    }

    @Override
    public boolean startPlugin(String pluginId) {
        return pluginManager.startPlugin(pluginId) == PluginState.STARTED;
    }

    @Override
    public boolean stopPlugin(String pluginId) {
        return pluginManager.stopPlugin(pluginId) == PluginState.STOPPED;
    }

    @Override
    public boolean reject(String pluginId) {
        return pluginManager.deletePlugin(pluginId) && pluginManager.unloadPlugin(pluginId);
    }
}
