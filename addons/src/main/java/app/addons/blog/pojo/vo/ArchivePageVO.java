package app.addons.blog.pojo.vo;

import app.isparks.core.pojo.base.BaseVO;
import app.isparks.core.pojo.dto.PostArchiveDTO;
import lombok.Data;

import java.util.List;

@Data
public class ArchivePageVO extends BaseVO {

    private List<PostArchiveDTO> archives;

}
