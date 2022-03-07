package app.isparks.plugin.pf4j;

import app.isparks.core.exception.PluginException;
import app.isparks.plugin.IPlugin;
import app.isparks.plugin.PluginState;
import org.pf4j.PluginWrapper;

public class PF4JPlugin  implements IPlugin {

    private final PluginWrapper wrapper;

    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper
     */
    public PF4JPlugin(PluginWrapper wrapper) {
        if(wrapper == null || wrapper.getPlugin() == null){
            throw new PluginException("生成插件失败");
        }

        this.wrapper = wrapper;
    }

    @Override
    public String getId() {
        return wrapper.getPluginId();
    }

    @Override
    public String getName() {
        return wrapper.getDescriptor().getPluginDescription();
    }

    @Override
    public String getAuthor() {
        return wrapper.getDescriptor().getProvider();
    }

    @Override
    public String getVersion() {
        return wrapper.getDescriptor().getVersion();
    }

    @Override
    public PluginState getState() {
        switch (wrapper.getPluginState()){
            case RESOLVED:return PluginState.LOADED;
            case STARTED: return PluginState.STARTED;
            case STOPPED: return PluginState.STOPPED;
            default: return PluginState.DISABLE;
        }
    }

}
