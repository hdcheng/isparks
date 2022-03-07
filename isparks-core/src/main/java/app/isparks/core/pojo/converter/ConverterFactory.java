package app.isparks.core.pojo.converter;

import org.mapstruct.factory.Mappers;

/**
 * @author chenghd
 * @date 2020/9/23
 */
public class ConverterFactory {

    public static <C> C get(Class<C> cClass){
        return Mappers.getMapper(cClass);
    }

}
