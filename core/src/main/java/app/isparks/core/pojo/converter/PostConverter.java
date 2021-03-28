package app.isparks.core.pojo.converter;

import app.isparks.core.pojo.dto.PostDTO;
import app.isparks.core.pojo.entity.Post;
import app.isparks.core.pojo.param.PostParam;
import app.isparks.core.pojo.param.PostUpdateParam;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/22
 */
@Mapper
public interface PostConverter {

    Post map(PostParam source);

    Post map(PostUpdateParam source);

    PostDTO map(Post source);

    List<PostDTO> maps(List<Post> source);

}
