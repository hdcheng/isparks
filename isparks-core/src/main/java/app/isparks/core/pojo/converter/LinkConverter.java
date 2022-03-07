package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.entity.Link;
import app.isparks.core.pojo.param.LinkParam;
import org.mapstruct.Mapper;

/**
 * @author： chenghd
 * @date： 2021/3/15
 */
@Mapper
public interface LinkConverter {


    LinkDTO map(Link link);

    Link map(LinkParam param);

}
