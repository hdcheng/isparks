package app.isparks.plugin;

import app.isparks.core.config.ISparksProperties;
import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import app.isparks.core.framework.enhance.WebPage;
import app.isparks.core.plugin.PluginInfo;
import app.isparks.core.plugin.PluginListener;
import app.isparks.core.plugin.PluginManager;
import app.isparks.core.plugin.PluginStatus;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.pojo.param.LinkParam;
import app.isparks.core.repository.BaseMapper;
import app.isparks.core.service.ILinkService;
import app.isparks.core.util.IOCUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.plugin.vo.Button;
import org.pf4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public abstract class AbstractPluginManager implements PluginManager {

    private Logger log = LoggerFactory.getLogger(AbstractPluginManager.class);

    // 基于 pf4j 实现插件
    private static org.pf4j.PluginManager pluginManager;

    private final static Map<String,PluginInfo> plugins = new ConcurrentHashMap<>();

    public AbstractPluginManager(){
        pluginManager = new org.pf4j.DefaultPluginManager(Paths.get(ISparksProperties.PLUGINS_FILE_PATH));
        refresh();
    }

    @Override
    public synchronized void refresh() {
        plugins().clear();
        pluginManager.loadPlugins();
        pluginManager.getPlugins().forEach(pluginWrapper -> {
            PluginInfo info = change(pluginWrapper);
            if(info != null){
                plugins.put(pluginWrapper.getPluginId(),info);
            }
        });
        startPlugins();
        log.info("plugins count {}",plugins.size());
    }

    private PluginInfo change(PluginWrapper wrapper){
        if(wrapper == null){
            return null;
        }
        PluginDescriptor descriptor = wrapper.getDescriptor();

        PluginInfo pluginInfo = new PluginInfo();
        pluginInfo.setId(descriptor.getPluginId());
        pluginInfo.setClassName(descriptor.getPluginClass());
        pluginInfo.setProvider(descriptor.getProvider());
        pluginInfo.setVersion(descriptor.getVersion());

        pluginInfo.setStatus(statusConverter(wrapper.getPluginState()));

        List<PluginDependency> dependencies = descriptor.getDependencies();
        List<String> ds = new ArrayList<>(dependencies.size());
        dependencies.forEach(d -> ds.add(d.getPluginId()));
        pluginInfo.setDependencies(ds);

        return pluginInfo;
    }

    private PluginStatus statusConverter(PluginState state){
        switch (state){
            case STOPPED:
                return PluginStatus.STOPPED;
            case STARTED:
                return PluginStatus.STARTED;
            case DISABLED:
                return PluginStatus.DISABLED;
            default:
                return PluginStatus.LOADED;
        }
    }

    @Override
    public int size() {
        return plugins.size();
    }

    @Override
    public List<PluginInfo> plugins() {
        List<PluginInfo> infos = new ArrayList<>(plugins.size());

        plugins.values().forEach(info -> infos.add(info));

        return infos;
    }

    @Override
    public List<PluginInfo> plugins(PluginStatus status) {

        List<PluginInfo> infos = new ArrayList<>();

        plugins().forEach( info -> {
            if(info.getStatus() == status){
                infos.add(info);
            }
        });

        return infos;
    }

    @Override
    public PluginInfo plugin(String pluginId) {
        return plugins.get(pluginId);
    }

    @Override
    public void load(String path) {
        Path p = Paths.get(path);
        try {
            pluginManager.loadPlugin(p);
            refresh();
        }catch (PluginRuntimeException e){
            log.error("加载插件异常",e);
        }
    }

    @Override
    public synchronized void loads() {
        pluginManager.loadPlugins();
        refresh();
    }

    @Override
    public synchronized void startPlugins() {
        pluginManager.startPlugins();
    }

    @Override
    public synchronized void startPlugin(String pluginId) {
        if(!plugins.containsKey(pluginId)){
            return;
        }
        pluginManager.startPlugin(pluginId);
    }

    @Override
    public synchronized void stopPlugins() {
        pluginManager.stopPlugins();
    }

    @Override
    public synchronized PluginStatus stopPlugin(String pluginId) {
        if(plugins.containsKey(pluginId)){
            return PluginStatus.DISABLED;
        }
        PluginState state = pluginManager.stopPlugin(pluginId);
        return statusConverter(state);
    }

    @Override
    public synchronized boolean deletePlugin(String id) {
        if(plugins.containsKey(id)){
            return pluginManager.deletePlugin(id);
        }
        return false;
    }

    public synchronized void addPlugin(String path,String name,String logo){
        if(StringUtils.hasEmpty(path,name,logo)){
            return;
        }

        String url = "/admin/plugin/"+path;

        url = url.replaceAll("/+","/");

        LinkParam param = new LinkParam();
        param.setName(name);
        param.setUrl(url);
        param.setLogo(logo);

        IOCUtils.getBeanByClass(ILinkService.class).ifPresent(linkService -> {
            linkService.save(param, LinkType.PLUGIN);
        });
    }


    public abstract <T> void registerHttpApi(Class<T> controllerType);

    public abstract <E extends ApplicationEvent> void registerAsynchronousListener(PluginListener<E> listener);

    public abstract <M extends BaseMapper> Optional<M> registerMyBatisMapper(String mapperName, Class<M> mapperClass);

    public abstract void registerWebPageEnhancer(AbstractViewModelEnhancer nextEnhancer, WebPage webPage);
}
