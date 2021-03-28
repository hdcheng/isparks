package app.isparks.core.pojo.converter;

import org.mapstruct.factory.Mappers;

/**
 * @author chenghd
 * @date 2020/8/22
 */
public abstract class BeanConverter {

    public static <T> T get(Class<T> tClass) {
        return Mappers.getMapper(tClass);
    }

}
