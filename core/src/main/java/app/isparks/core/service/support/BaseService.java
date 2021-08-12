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
    public void notNull(Object[] array, String msg) {
        Assert.notEmpty(array, msg);
    }

    /**
     * 非空
     */
    public void notEmpty(Collection<?> collection, String msg) {
        Assert.notEmpty(collection, msg);
    }

    /**
     * 字符串非空
     */
    public void notEmpty(String text, String message) {
        Assert.hasLength(text, message);
    }



    /**
     * Set result message use ThreadLocal .
     * 使用 ThreadLocal 设置消息
     */
    protected String  resultMessage(String msg){
        // todo: 使用 ThreadLocal 设置异常消息等。

        return msg;
    }



}
