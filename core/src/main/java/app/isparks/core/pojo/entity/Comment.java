package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * 评论
 *
 * @author chenghd
 * @date 2020/8/18
 */
@Data
public class Comment extends BaseEntity {

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
     * 评论者ip
     */
    private String ip;

}
