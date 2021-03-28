package app.isparks.core.util;

import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.exception.SystemException;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenghd
 * @date 2020/9/21
 */
public class BeanUtils {

    private final static Map<String,BeanCopier> BEAN_COPIER_CONTAINER = new ConcurrentHashMap<>();

    /**
     * 复制对象属性
     * 复制 source的属性到 target 中
     */
    public static Object copyProperties(Object source, Object target) {
        try {
            getCopier(source,target).copy(source,target,null);
            //org.springframework.beans.BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new SystemException("属性复制失败");
        }
        return target;
    }

    /**
     * 复制对象属性
     *
     * @return T实例 / null
     */
    public static <T> T copyProperties(Object source, Class<T> tClass) {
        T target = null;
        try {
            target = tClass.newInstance();
            getCopier(source.getClass(),tClass).copy(source,target,null);
            //copyProperties(source, target);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SystemException("属性复制失败");
        } finally {
            return target;
        }
    }

    /**
     * 更细数据
     *
     * @param source
     * @param target
     */
    public static void updateProperties(Object source,Object target){
        if (source == null || target == null){
            throw new InvalidValueException("source and target must not be null.");
        }
        try {
            org.springframework.beans.BeanUtils.copyProperties(source,target,getNullPropertyNames(source));
            //copyProperties(source, target);
        } catch (BeansException e) {
            throw new SystemException("属性复制失败");
        }
    }

    /**
     * 复制对象属性
     *
     * @return Optional.ofNullable()
     */
    public static <T> Optional<T> copyPropertiesOptional(Object source, Class<T> tClass) {

        if (source == null)
            return Optional.empty();

        return Optional.of(copyProperties(source, tClass));
    }

    /**
     * 复制多个对象的属性
     * S : 源对象类型
     * T : 目标对象类型
     */
    public static <S, T> List<T> copyProperties(List<S> source, Class<S> sClass, Class<T> tClass) {
        int size = source.size();
        List<T> ts = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            ts.add(copyProperties(source.get(i), tClass));
        }
        return ts;
    }

    public static <S, T> Optional<List<T>> copyPropertiesOptional(List<S> source, Class<S> sClass, Class<T> tClass) {
        if (source == null || source.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(copyProperties(source, sClass, tClass));
    }

    /**
     * 获取缓存的 BeanCopier 对象
     */
    public static <S,T> BeanCopier getCopier(Class<S> sClass,Class<T> tClass){

        String classKey = new StringBuilder().append(sClass.toString()).append("@").append(tClass.toString()).toString();

        BeanCopier copier = BEAN_COPIER_CONTAINER.get(classKey);

        if(copier != null){
            return copier;
        }

        copier = BeanCopier.create(sClass,tClass,false);
        BEAN_COPIER_CONTAINER.put(classKey,copier);//缓存
        return  copier;
    }

    /**
     * 获取缓存的 BeanCopier 对象
     */
    public static <S,T> BeanCopier getCopier(S s,T t){
        return getCopier(s.getClass(),t.getClass());
    }


    /**
     * Gets null names array of property.
     *
     * @param source object data must not be null
     * @return null name array of property
     */
    private static String[] getNullPropertyNames(@NonNull Object source) {
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    /**
     * Gets null names set of property.
     *
     * @param source object data must not be null
     * @return null name set of property
     */
    private static Set<String> getNullPropertyNameSet(@NonNull Object source) {

        Assert.notNull(source, "source object must not be null");
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            // if property value is equal to null, add it to empty name set
            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }

        return emptyNames;
    }

}
