package app.isparks.plugin;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.exception.SystemException;
import app.isparks.plugin.enhance.AbstractViewModelEnhancer;
import app.isparks.plugin.enhance.web.WebPage;
import app.isparks.core.plugin.PluginListener;
import app.isparks.core.repository.BaseMapper;
import app.isparks.core.util.IOCUtils;
import app.isparks.plugin.enhance.web.IndexPageEnhancer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.MethodIntrospector;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： chenghd
 * @date： 2021/3/17
 */
public final class DefaultPluginManager extends AbstractPluginManager {

    private static Logger log = LoggerFactory.getLogger(DefaultPluginManager.class);

    private AbstractViewModelEnhancer postPageEnhancer;

    private static volatile RequestMappingHandlerMapping requestMappingHandlerMapping;

    private static Map<String, AbstractViewModelEnhancer> enhancers = new ConcurrentHashMap<>();

    private static DefaultPluginManager defaultPluginManager;

    private DefaultPluginManager(){
        super();
        requestMappingHandlerMapping = (RequestMappingHandlerMapping) IOCUtils.getBeanByName("requestMappingHandlerMapping").orElseThrow( () ->new SystemException("RequestMappingHandlerMapping bean no find"));
    }

    /**
     * 获取实例
     */
    public static DefaultPluginManager instance(){
        if(defaultPluginManager == null){
            synchronized (AbstractPluginManager.class){
                if(defaultPluginManager == null){
                    defaultPluginManager = new DefaultPluginManager();
                }
            }
        }

        return defaultPluginManager;
    }

    /**
     * 获取 RequestMappingHandlerMapping 对象用于注册前端接口
     */
    private RequestMappingHandlerMapping getRequestMappingHandlerMapping(){
        if(requestMappingHandlerMapping == null){
            synchronized (AbstractPluginManager.class){
                if(requestMappingHandlerMapping == null){
                    requestMappingHandlerMapping = (RequestMappingHandlerMapping) IOCUtils.getBeanByName("requestMappingHandlerMapping").orElseThrow( () ->new SystemException("RequestMappingHandlerMapping bean no find"));
                }
            }
        }
        return requestMappingHandlerMapping;
    }

    /**
     * 注册API
     */
    @Override
    public <T> void registerHttpApi(Class<T> controllerType) {
        try {
            // 先注册成bean
            IOCUtils.registerBean(controllerType);

            IOCUtils.getBeanByClass(controllerType).ifPresent(controller -> {
                RequestMappingHandlerMapping rmm = getRequestMappingHandlerMapping();
                //see : AbstractHandlerMethodMapping line:268
                Map<Method, RequestMappingInfo> methods = resolveAnnotationInfoFromController(controllerType);
                methods.forEach((method,mapping)->{
                    rmm.registerMapping(mapping,controller,method);
                });
            });

        }catch (Exception e){
            log.error("注册Controller失败",e);
        }
    }

    /**
     * 解析类上的注解
     */
    private <T> Map<Method,RequestMappingInfo> resolveAnnotationInfoFromController(Class<T> controllerType){

        Map<Method,RequestMappingInfo> methods = MethodIntrospector.
                selectMethods(controllerType,(MethodIntrospector.MetadataLookup<RequestMappingInfo>) method -> getMappingForMethod(method,controllerType));

        return methods == null ? new HashMap<>() : methods;
    }

    /**
     * 解析方法上的注解
     */
    private RequestMappingInfo getMappingForMethod(Method method,Class controllerType){

        RequestMapping classMappingAnnotation = (RequestMapping)controllerType.getAnnotation(RequestMapping.class);
        String[] parentPaths = null;
        if(classMappingAnnotation != null){
            parentPaths = classMappingAnnotation.value();
        }

        RequestMapping mappingAnnotation = method.getAnnotation(RequestMapping.class);
        if(mappingAnnotation != null){
            String[] paths = mappingAnnotation.value();
            RequestMethod[] methods = mappingAnnotation.method();
            String[] headers = mappingAnnotation.headers();

            String[] urls ;
            if(parentPaths != null && parentPaths.length != 0){
                List<String> us = new ArrayList<>(parentPaths.length * paths.length);
                for(String pp : parentPaths){
                    for(String cp : paths){
                        String tmp = pp + ISparksConstant.URL_SEPARATOR + cp;
                        tmp = tmp.replaceAll("/+",ISparksConstant.URL_SEPARATOR);
                        us.add(tmp);
                    }
                }

                urls = new String[us.size()];
                us.toArray(urls);
            }else{
                urls = paths;
            }

            return RequestMappingInfo
                    //.paths(paths)
                    .paths(urls)
                    .methods(methods)
                    .headers(headers)
                    .build();
        }
        return null;
    }

    /**
     * 注册异步监听事件
     */
    @Override
    public <E extends ApplicationEvent> void registerAsynchronousListener(PluginListener<E> listener) {

    }

    /**
     * 注册mapper
     */
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

    /**
     * 注册增强器
     */
    @Override
    public synchronized void registerWebPageEnhancer(AbstractViewModelEnhancer nextEnhancer, WebPage webPage) {

        AbstractViewModelEnhancer enhancer = enhancers.get(webPage.toString());

        if(enhancer == null){
            switch (webPage){
                case INDEX:
                    enhancer = IndexPageEnhancer.singleton(); break;
            }
        }

        enhancer.setNextEnhancer(nextEnhancer);

        enhancers.put(webPage.toString(),nextEnhancer);

    }

}
