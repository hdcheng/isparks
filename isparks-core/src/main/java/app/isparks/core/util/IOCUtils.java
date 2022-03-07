package app.isparks.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/9/1
 */
public class IOCUtils {

    private static Logger log = LoggerFactory.getLogger(IOCUtils.class);

    public static ApplicationContext applicationContext;

    public static DefaultListableBeanFactory beanFactory = null;

    /**
     * 获取 bean
     *
     * @param name
     * @return
     */
    public static Optional<Object> getBeanByName(String name) {
        check();

        try {
            Object bean = applicationContext.getBean(name);
            if (bean != null) {
                log.info("获取bean[{}]成功", name);
            }
            return Optional.ofNullable(bean);
        } catch (BeansException e) {
            log.error("获取bean[{}]失败", name, e);
        }
        return Optional.empty();
    }

    /**
     * 注册单例 bean
     */
    public static Optional<Object> registerSingleton(String beanName,Object object){

        check();

        if( object != null && !beanFactory.containsBean(beanName) ){

            beanFactory.registerSingleton(beanName,object);

            log.info("注册bean[{}]成功", beanName);

            return Optional.of(object);

        }else{

            log.error("注册bean[{}]失败", beanName);

        }

        return Optional.empty();
    }

    /**
     * 根据类型获取bean
     *
     * @param c
     * @param <H>
     * @return
     */
    public static <H> Optional<Map<String, H>> getBeansByClass(Class<H> c) {
        check();
        try {
            Map<String, H> beans = applicationContext.getBeansOfType(c);
            if (beans != null) {
                log.info("获取bean[{}]成功", c);
            }
            return Optional.ofNullable(beans);
        } catch (BeansException e) {
            log.error("获取bean[{}]失败", c, e);
        }
        return Optional.empty();
    }

    /**
     *
     */
    public static <H> Optional<H> getBeanByClass(Class<H> c) {
        check();
        try {
            H bean = applicationContext.getBean(c);
            if (bean != null) {
                log.info("获取bean[{}]成功", c);
            }
            return Optional.ofNullable(bean);
        } catch (BeansException e) {
            log.error("获取bean[{}]失败", c, e);
        }
        return Optional.empty();
    }

    /**
     * 注册 bean
     *
     * @param tClass
     * @param beanName
     * @param <T>
     */
    public static <T> void registerBean(Class<T> tClass, String beanName) {
        check();

        try {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(tClass);
            if(beanFactory.containsBean(beanName)){
                log.warn("注册失败，bean[{}] 已存在", beanName);
                return;
            }
            //beanFactory.registerSingleton(beanName, builder.getBeanDefinition());
            beanFactory.registerBeanDefinition(beanName, builder.getBeanDefinition());
            log.info("注册 bean[{}] 成功", beanName);
        } catch (BeanDefinitionStoreException e) {
            log.info("注册 bean[{}] 失败", beanName, e);
        }
    }

    /**
     *
     * @param tClass
     * @param <T>
     */
    public static <T> void registerBean(Class<T> tClass) {
        check();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(tClass);
        AbstractBeanDefinition beanDefinition= builder.getBeanDefinition();
        try {

            if(beanFactory.containsBean(beanDefinition.getBeanClassName())){
                log.warn("注册失败，bean[{}] 已存在", beanDefinition.getBeanClassName());
                return;
            }
            beanFactory.registerBeanDefinition(beanDefinition.getBeanClassName(),beanDefinition);
            log.info("注册 bean[{}] 成功", beanDefinition.getBeanClassName());
        } catch (BeanDefinitionStoreException e) {
            log.info("注册 bean[{}] 失败", beanDefinition.getBeanClassName() , e);
        }
    }

    /**
     * 获取应用上下文
     */
    public static ApplicationContext getApplicationContext(){
        check();
        return applicationContext;
    }


    /**
     * 移除指定类型的 bean
     * @param tClass
     */
    public static <T> void removeBean(Class<T> tClass){
        if(tClass == null){
            return;
        }

        getBeansByClass(tClass).ifPresent(beans -> {
            beans.entrySet().forEach(bean -> {
                beanFactory.removeBeanDefinition(bean.getKey());
            });
        });
    }

    private static void check() {
        if (applicationContext == null) {
            throw new RuntimeException("系统还未初始化完成");
        }
        if(beanFactory == null){
            synchronized (IOCUtils.class){
                if(beanFactory == null){
                    beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
                }
            }
        }
    }
}
