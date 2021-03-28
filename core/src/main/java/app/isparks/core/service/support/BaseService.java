package app.isparks.core.service.support;

import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author chenghd
 * @date 2020/8/14
 */
public abstract class BaseService  {


    /**
     * 判断Object是否为null
     */
    public boolean isNull(Object o){
        return o == null;
    }

    /**
     * Object不为空
     */
    public boolean notNull(Object o){
        return o != null;
    }

    public <T,R> Optional<R> toDTO(Optional<T> target, Function<T,R> convertFunction){
        R r = target.isPresent() ? convertFunction.apply(target.get()) : null;
        return Optional.ofNullable(r);
    }

    /**
     * 非空检测
     */
    public void notNull(Object o, String msg) {
        Assert.notNull(o, msg);
    }

    /**
     * 非空
     */
    public static void notNull(Object[] array, String msg) {
        Assert.notEmpty(array, msg);
    }

    /**
     * 非空
     */
    public static void notEmpty(Collection<?> collection, String msg) {
        Assert.notEmpty(collection, msg);
    }

    /**
     * 字符串非空
     */
    public static void notEmpty(String text, String message) {
        Assert.hasLength(text, message);
    }

    /**
     * 字符串非空
     */
    public static void notEmpty(String[] texts, String message) {
        for (String text : texts) {
            notEmpty(text, message);
        }
    }

}
