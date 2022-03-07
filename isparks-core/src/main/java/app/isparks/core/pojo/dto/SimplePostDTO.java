package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * @author： chenghd
 * @date： 2021/3/27
 */
@Data
public class SimplePostDTO extends BaseDTO {

    /**
     * post title
     */
    private String title;

    /**
     * post url
     */
    private String url;

    /**
     * post summary
     * 摘要
     */
    private String summary;

}
