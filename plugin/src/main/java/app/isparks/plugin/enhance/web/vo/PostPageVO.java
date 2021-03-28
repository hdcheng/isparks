package app.isparks.plugin.enhance.web.vo;


import app.isparks.core.pojo.base.WebPageVO;
import app.isparks.plugin.enhance.web.dto.IndexPostDTO;
import lombok.Data;

/**
 * @author： chenghd
 * @date： 2021/3/27
 */
@Data
public class PostPageVO extends WebPageVO<IndexPostDTO> {

    /**
     * 主键ID
     */
    private String id;

    /**
     * unix time
     */
    private Long createTime;

    /**
     * unix time
     */
    private Long modifyTime;

    /**
     * status.
     */
    private Integer status;

    private String originContent;

    private String title;

}
