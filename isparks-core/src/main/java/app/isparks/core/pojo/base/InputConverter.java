package app.isparks.core.pojo.base;


import app.isparks.core.util.BeanUtils;
import app.isparks.core.util.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 *
 * @author： chenghd
 * @date： 2021/1/19
 */
public interface InputConverter<DOMAIN> {


    default DOMAIN toDo(){

        ParameterizedType currentType = parameterizedType();

        // Assert not equal
        Objects.requireNonNull(currentType, "Cannot fetch actual type because parameterized type is null");

        Class<DOMAIN> domainClass = (Class<DOMAIN>) currentType.getActualTypeArguments()[0];

        return BeanUtils.copyProperties(this,domainClass);

    }


    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());
    }
}
