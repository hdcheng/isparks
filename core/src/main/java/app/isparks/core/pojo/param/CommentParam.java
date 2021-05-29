package app.isparks.core.pojo.param;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentParam {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人名称
     */
    private String name;

    /**
     * 评论人邮箱
     */
    private String email;

    /**
     * 评论人的网站地址
     */
    private String address;

    /**
     * 评论人的网站地址
     */
    private String ip;

    /**
     * post id
     */
    private String postId;

}
