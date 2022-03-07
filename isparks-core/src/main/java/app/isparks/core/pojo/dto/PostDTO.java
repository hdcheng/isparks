package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;


/**
 * @author chenghd
 * @date 2020/8/11
 */
@Data
public class PostDTO extends BaseDTO {

    /**
     * post title
     */
    private String title;

    /**
     * post url
     */
    private String url;

    /**
     * post origin content
     * 没有任何格式处理，原文本内容
     */
    private String originContent;

    /**
     * post summary
     * 摘要
     */
    private String summary;

    /**
     * 状态
     */
    private String status;

}
