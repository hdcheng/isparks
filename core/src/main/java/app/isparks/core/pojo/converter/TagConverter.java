package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.TagDTO;
import app.isparks.core.pojo.entity.Tag;
import app.isparks.core.pojo.param.TagParam;
import app.isparks.core.pojo.param.TagUpdateParam;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/22
 */
@Mapper
public interface TagConverter {

    Tag map(TagParam source);

    Tag map(TagUpdateParam source);

    TagDTO map(Tag source);

    List<TagDTO> maps(List<Tag> source);

}
