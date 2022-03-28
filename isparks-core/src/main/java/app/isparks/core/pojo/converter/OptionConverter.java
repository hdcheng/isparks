package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.entity.Option;
import app.isparks.core.pojo.param.OptionParam;
import org.mapstruct.Mapper;

/**
 * @author： chenghd
 * @date： 2021/1/14
 */
@Mapper
public interface OptionConverter {

    Option map(OptionParam param);

}
