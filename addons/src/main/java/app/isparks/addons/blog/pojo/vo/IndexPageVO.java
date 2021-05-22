package app.isparks.addons.blog.pojo.vo;

import app.isparks.addons.blog.pojo.dto.IndexPostDTO;
import lombok.Data;

/**
 * @author： chenghd
 * @date： 2021/3/27
 */
@Data
public class IndexPageVO extends WebPageVO<IndexPostDTO> {

    private String footer;

}
