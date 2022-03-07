package app.isparks.dao.repository;

import app.isparks.core.pojo.entity.Comment;
import app.isparks.core.pojo.entity.relation.PostCommentRL;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.page.PageInfo;
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
    public abstract List<PostCommentRL> selectByPost(String postId);

    /**
     * 统计 post 下有多少个
     */
    public abstract int countByPost(String postId);

    /**
     * 根据post id查找comment数据
     */
    public abstract PageData<Comment> pageCommentByPost(PageInfo pageInfo,Comment cond,String postId);

}
