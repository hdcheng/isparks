package app.isparks.plugin;

import java.util.List;

/**
 * @author： chenghd
 * @date： 2021/3/17
 */
public interface IPluginManager {

    /**
     * 插件列表
     */
    List<IPlugin> plugins();

    /**
     * 根据 id 查找插件
     * @param id
     */
    IPlugin plugin(String id);

    /**
     * 注册插件
     * @param plugin
     */
    boolean register(IPlugin plugin);

    /**
     * 启动指定插件
     */
    boolean startPlugin(String pluginId);

    /**
     * 停止指定的插件，并返回插件状态
     */
    boolean stopPlugin(String pluginId);

    /**
     * 移除插件
     */
    boolean reject(String pluginId);

}
