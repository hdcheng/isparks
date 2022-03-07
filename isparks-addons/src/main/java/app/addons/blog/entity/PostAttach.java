package app.addons.blog.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * blog post 信息
 *
 * @author： chenghd
 * @date： 2021/3/20
 */
@Setter
@Getter
public class PostAttach extends BaseEntity {

    private Long visits;

    private Long likes;

    private String postId;

}
