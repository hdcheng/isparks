package app.isparks.dao.repository;


import app.isparks.core.pojo.entity.relation.PostTagRL;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/8/20
 */
public abstract class PostTagRLCurd extends AbstractCurd<PostTagRL> {

    @Override
    public PostTagRL newEntity() {
        return new PostTagRL();
    }

    /**
     * 保存多个
     *
     * @return
     */
    public abstract List<PostTagRL> saveIfAbsent(List<PostTagRL> postTagRLS);

    /**
     * delete by post
     */
    public abstract boolean deleteByPost(String postId);

    /**
     * delete by tag
     */
    public abstract boolean deleteByTag(String tagId);

    /**
     *
     */
    public abstract List<PostTagRL> findByPost(String postId);

    /**
     *
     */
    public abstract List<PostTagRL> findByTag(String tagId);

    /**
     *
     */
    public abstract PostTagRL findByPostAndTag(String postId, String tagId);

    /**
     *
     */
    public abstract int countByPost(String postId);

    /**
     *
     */
    public abstract int countByTag(String tagId);

    /**
     *
     */
    public abstract List<PostTagRL> query();

    /**
     *
     */
    public abstract PageData<PostTagRL> query(PageInfo info);
}


