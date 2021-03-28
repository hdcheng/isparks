package app.isparks.plugin.enhance.web.vo;

import app.isparks.core.pojo.base.WebPageVO;
import app.isparks.plugin.enhance.web.dto.IndexPostDTO;
import lombok.Data;

/**
 * @author： chenghd
 * @date： 2021/3/27
 */
@Data
public class IndexPageVO extends WebPageVO<IndexPostDTO> {

    private String footer;

}
