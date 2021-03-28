package app.isparks.plugin;

import app.isparks.core.exception.SystemException;
import app.isparks.core.plugin.PluginListener;
import app.isparks.core.pojo.entity.Link;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.repository.BaseMapper;
import app.isparks.core.service.ILinkService;
import app.isparks.core.util.IOCUtils;
import app.isparks.core.framework.enhance.AbstractViewModelEnhancer;
import app.isparks.core.framework.enhance.WebPage;
import app.isparks.core.web.support.BaseApi;
import app.isparks.plugin.enhance.web.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.MethodIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： chenghd
 * @date： 2021/3/17
 */
@Component
public class DefaultPluginManager extends AbstractPluginManager {

    private Logger log = LoggerFactory.getLogger(DefaultPluginManager.class);

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private AbstractViewModelEnhancer postPageEnhancer;

    private Map<String,AbstractViewModelEnhancer> enhancers = new ConcurrentHashMap<>();

    @Override
    public void setLinkButton(String htmlFileName, String pluginName, String htmlDom) {
        IOCUtils.getBeanByClass(ILinkService.class).ifPresent((linkService) -> {
            initLink(linkService,htmlFileName,pluginName,htmlDom);
        });
    }

    @Override
    public void registerHttpApi(String[] urls, RequestMethod[] methods, BaseApi controller, String method, Class<?>... parameterTypes) {
        try {
            RequestMappingInfo info = RequestMappingInfo.paths(urls).methods(methods).build();

            RequestMappingHandlerMapping rmm = getRequestMappingHandlerMapping();

            rmm.registerMapping(info,controller,controller.getClass().getMethod(method,parameterTypes));

        }catch (Exception e){
            log.error("注册Controller失败",e);
        }
    }

    @Override
    public <T> void registerHttpApi(Class<T> controllerType) {
        try {
            IOCUtils.getBeanByClass(controllerType).ifPresent(controller -> {
                RequestMappingHandlerMapping rmm = getRequestMappingHandlerMapping();
                //see : AbstractHandlerMethodMapping line:268
                Map<Method,RequestMappingInfo> methods = resolveAnnotationInfoFromController(controllerType);
                methods.forEach((method,mapping)->{
                    rmm.registerMapping(mapping,controller,method);
                });
            });
        }catch (Exception e){
            log.error("注册Controller失败",e);
        }
    }

    private <T> Map<Method,RequestMappingInfo> resolveAnnotationInfoFromController(Class<T> controllerType){

        Map<Method,RequestMappingInfo> methods = MethodIntrospector.
                selectMethods(controllerType,(MethodIntrospector.MetadataLookup<RequestMappingInfo>) method -> getMappingForMethod(method,controllerType));

        return methods == null ? new HashMap<>() : methods;
    }

    private RequestMappingInfo getMappingForMethod(Method method,Class controllerType){
        RequestMapping mappingAnnotation = method.getAnnotation(RequestMapping.class);
        if(mappingAnnotation != null){
            String[] paths = mappingAnnotation.value();
            RequestMethod[] methods = mappingAnnotation.method();
            String[] headers = mappingAnnotation.headers();

            return RequestMappingInfo
                    .paths(paths)
                    .methods(methods)
                    .headers(headers)
                    .build();
        }
        return null;
    }

    @Override
    public <E extends ApplicationEvent> void registerAsynchronousListener(PluginListener<E> listener) {


    }

    @Override
    public <M extends BaseMapper> Optional<M> registerMyBatisMapper(String mapperName, Class<M> mapperClass) {
        Optional<SqlSessionFactory> sqlSessionFactory = IOCUtils.getBeanByClass(SqlSessionFactory.class);

        if(sqlSessionFactory.isPresent()){

            DefaultSqlSessionFactory sessionFactory = (DefaultSqlSessionFactory)sqlSessionFactory.get();
            M mapper = sessionFactory.openSession().getMapper(mapperClass);
            IOCUtils.registerSingleton(mapperName,mapper);

            return Optional.ofNullable(mapper);
        }
        return Optional.empty();
    }

    @Override
    public synchronized void registerWebPageEnhancer(AbstractViewModelEnhancer nextEnhancer, WebPage webPage) {

        AbstractViewModelEnhancer enhancer =enhancers.get(webPage.toString());

        if(enhancer == null){
            switch (webPage){
                case INDEX:
                    enhancer = IndexPageEnhancer.singleton(); break;
                case POST:
                    enhancer = PostPageEnhancer.singleton();break;
                case ARCHIVE:
                    enhancer = ArchivePageEnhancer.singleton();break;
                case LINK:
                    enhancer = LinkPageEnhancer.singleton();break;
                case ABOUT:
                    enhancer = AboutPageEnhancer.singleton();break;
                case GALLERY:
                    enhancer = GalleryPageEnhancer.singleton();break;
                case CATEGORY:
                    enhancer = CategoryPageEnhancer.singleton();break;
                case TAG:
                    enhancer = TagPageEnhancer.singleton();break;
                default:
                    enhancer = OtherPageEnhancer.singleton();
            }
        }

        enhancer.setNextEnhancer(nextEnhancer);

        enhancers.put(webPage.toString(),nextEnhancer);

    }

    /**
     * 获取RequestMappingHandlerMapping实例
     */
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping(){
        if(requestMappingHandlerMapping == null){
            synchronized (this){
                if(requestMappingHandlerMapping == null){
                    requestMappingHandlerMapping = (RequestMappingHandlerMapping) IOCUtils
                            .getBeanByName("requestMappingHandlerMapping")
                            .orElseThrow( () ->new SystemException("bean no find"));
                }
            }
        }
        return requestMappingHandlerMapping;
    }

    /**
     * 初始化Link数据
     */
    private void initLink(ILinkService linkService, String htmlFileName, String pluginName, String htmlDom){

        Link link = new Link();
        link.setUrl("http://127.0.0.1:8174/admin/plugin/"+htmlFileName);
        //link.setLogo("<span uk-icon='image' class='uk-box-shadow-hover-small'></span>");
        link.setLogo(htmlDom);
        link.setName(pluginName);
        link.setType(LinkType.PLUGIN.getCode());
        linkService.saveIfUrlAbsent(link);
    }

}
