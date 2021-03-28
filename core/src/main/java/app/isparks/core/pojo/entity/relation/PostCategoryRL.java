package app.isparks.core.pojo.entity.relation;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * 文章 和 分类 关联对象
 *
 * @author chenghd
 * @date 2020/8/11
 */
@Data
public class PostCategoryRL extends BaseEntity {

    /**
     * post id
     */
    private String postId;

    /**
     * category id
     */
    private String categoryId;

    public PostCategoryRL withPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public PostCategoryRL withCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

}
