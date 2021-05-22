package app.isparks.dao.repository;

import app.isparks.core.pojo.entity.relation.PostCommentRL;
import app.isparks.dao.template.AbstractCurd;

import java.util.List;

public abstract class AbstractPostCommentRLCurd extends AbstractCurd<PostCommentRL> {

    @Override
    public PostCommentRL newEntity() {
        return new PostCommentRL();
    }

    /**
     * delete by post
     */
    public abstract boolean deleteByPost(String postId);

    /**
     * delete by tag
     */
    public abstract boolean deleteByComment(String comment);

    /**
     * 根据 post id 查找评论
     */
    public abstract List<PostCommentRL> findByPost(String postId);

    /**
     * 统计 post 下有多少个
     */
    public abstract int countByPost(String postId);

}
