package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.CommentDTO;
import app.isparks.core.pojo.entity.Comment;
import org.mapstruct.Mapper;

/**
 * @author chenghd
 * @date 2020/8/22
 */
@Mapper
public interface CommentConverter {

    CommentDTO map(Comment source);

}
