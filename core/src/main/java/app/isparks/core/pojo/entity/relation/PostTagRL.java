package app.isparks.core.pojo.entity.relation;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * 文章 标签 关联对象
 *
 * @author chenghd
 * @date 2020/8/11
 */
@Data
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

}
