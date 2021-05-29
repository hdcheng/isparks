package app.isparks.core.plugin;

import java.util.List;

/**
 * @author： chenghd
 * @date： 2021/3/17
 */
public interface PluginManager {

    /**
     * 插件总数量
     */
    int size();

    /**
     * 所有插件信息
     */
    List<PluginInfo> plugins();

    /**
     * 根据状态获取插件信息
     */
    List<PluginInfo> plugins(PluginStatus status);

    /**
     * 获取插件
     */
    PluginInfo plugin(String pluginId);

    /**
     * 根据路径加载插件
     */
    void load(String path);

    /**
     * 批量加载
     */
    void loads();

    /**
     * 启动所有插件
     */
    void startPlugins();

    /**
     * 启动指定插件
     */
    void startPlugin(String pluginId);

    /**
     * 关闭所有启动的插件
     */
    void stopPlugins();

    /**
     * 停止指定的插件，并返回插件状态
     */
    PluginStatus stopPlugin(String pluginId);

    /**
     * 删除已经停止和无效的插件
     */
    boolean deletePlugin(String id);


    /**
     * 刷新
     */
    void refresh();
}
