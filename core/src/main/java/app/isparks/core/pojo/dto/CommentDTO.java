package app.isparks.core.pojo.dto;


import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/8/18
 */
@Data
public class CommentDTO extends BaseDTO {

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


}
