package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Post;
import app.isparks.dao.mybatis.mapper.PostMapper;
import app.isparks.dao.repository.AbstractPostCurd;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author chenghd
 * @date 2020/8/11
 */
@Repository
public class PostCurdImpl extends AbstractPostCurd {

    private PostMapper postMapper;

    public PostCurdImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> selectByCond(Post post) {
        return postMapper.selectByCond(post);
    }

    @Override
    public Post insert(final Post t) {
        beforeInsert(t);
        return postMapper.insert(t) == 1 ? t : null;
    }

    @Override
    public Post updateById(final Post post) {
        return postMapper.update(post) == 1 ? post : null;
    }

    @Override
    protected int deleteBy(Post post) {
        return postMapper.deleteByCond(post);
    }

    @Override
    public long countBy(Post post) {
        return postMapper.countByCond(post);
    }
}
