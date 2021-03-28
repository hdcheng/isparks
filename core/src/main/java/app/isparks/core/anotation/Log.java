package app.isparks.core.anotation;




import app.isparks.core.pojo.enums.LogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String value() default "";

    String description() default "";

    /**
     * 日志类型 / 调用的接口（方法）类型
     */
    LogType[] types() default LogType.UNKNOWN;
}
