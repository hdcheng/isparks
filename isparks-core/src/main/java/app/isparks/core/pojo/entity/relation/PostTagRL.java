package app.isparks.core.pojo.entity.relation;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * 文章 标签 关联对象
 *
 * @author chenghd
 * @date 2020/8/11
 */
public class PostTagRL extends BaseEntity {

    /**
     * 文章id
     */
    private String postId;

    /**
     * 标签id
     */
    private String tagId;

    public PostTagRL withPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public PostTagRL withTagId(String tagId) {
        this.tagId = tagId;
        return this;
    }


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
