package app.isparks.addons.blog.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * blog post 信息
 *
 * @author： chenghd
 * @date： 2021/3/20
 */
@Data
public class PostAttach extends BaseEntity {

    private Long visits;

    private Long likes;

    private String postId;

}
