package app.isparks.core.plugin;

/**
 * 基础插件接口
 *
 * @author chenghd
 * @date 2020/8/4
 */
public interface IPlugin {

    /**
     * 插件入口
     */
    void activate(Object var);

    /**
     * 插件释放调用
     */
    void deactivate();

}
