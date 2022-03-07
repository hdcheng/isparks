package app.isparks.plugin;

public interface IPlugin {

    /**
     * 获取插件 id
     * @return
     */
    String getId();

    /**
     * 插件名
     * @return
     */
    String getName();

    /**
     * 插件作者
     * @return
     */
    String getAuthor();

    /**
     * 插件版本
     * @return
     */
    String getVersion();


    /**
     * 状态
     */
    PluginState getState();


}
