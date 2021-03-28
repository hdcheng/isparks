package app.isparks.core.plugin;

import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import app.isparks.core.framework.enhance.WebPage;
import app.isparks.core.repository.BaseMapper;
import app.isparks.core.web.support.BaseApi;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

/**
 * @author： chenghd
 * @date： 2021/3/17
 */
public interface PluginManager {

    /**
     * 插件数量
     */
    int size();

    /**
     * 安装插件
     * @param name 插件名
     * @param plugin 插件实例
     */
    IPlugin install(String name,final IPlugin plugin);

    /**
     * 卸载已经注册的插件
     * @param name 插件名
     */
    void uninstall(String name);

    /**
     * 这是插件前端显示按钮
     *
     * @param htmlFileName 插件前端页面名
     * @param pluginName 插件名称
     * @param htmlDom 显示样式，如：<span uk-icon='image'></span>
     */
    void setLinkButton(String htmlFileName, String pluginName, String htmlDom);

    /**
     * 注册 http 请求接口
     *
     * @param urls
     * @param controller
     * @param method
     */
    void registerHttpApi(String[] urls, RequestMethod[] methods, BaseApi controller, String method, Class<?>... parameterTypes);

    /**
     * 注册API
     */
    <T> void registerHttpApi(Class<T> controller);

    /**
     * 注册异步监听事件
     */
    <E extends ApplicationEvent> void registerAsynchronousListener(PluginListener<E> listener);

    /**
     * 注册mapper
     */
    <M extends BaseMapper> Optional<M> registerMyBatisMapper(String beanName, Class<M> mapper);

    /**
     * 注册web页面增强器
     */
    void registerWebPageEnhancer(AbstractViewModelEnhancer enhancer, WebPage webPage);
}
