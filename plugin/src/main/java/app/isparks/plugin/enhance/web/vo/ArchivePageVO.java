package app.isparks.plugin.enhance.web.vo;

import app.isparks.core.pojo.base.WebPageVO;
import app.isparks.core.pojo.dto.PostArchiveDTO;
import lombok.Data;

import java.util.List;


/**
 * @author： chenghd
 * @date： 2021/3/27
 */
@Data
public class ArchivePageVO extends WebPageVO {

    private List<PostArchiveDTO> archives;

}
