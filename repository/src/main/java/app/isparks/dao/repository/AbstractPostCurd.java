package app.isparks.dao.repository;




import app.isparks.core.pojo.entity.Post;
import app.isparks.dao.template.AbstractCurd;

import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/8/11
 */
public abstract class AbstractPostCurd extends AbstractCurd<Post> {

    @Override
    public Post newEntity() {
        return new Post();
    }


}
