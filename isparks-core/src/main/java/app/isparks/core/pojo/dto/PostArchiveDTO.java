package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseProperty;
import lombok.Data;

import java.util.List;

/**
 * @author： chenghd
 * @date： 2021/3/27
 */
@Data
public class PostArchiveDTO extends BaseProperty {

    /**
     * year / month
     */
    private Integer date;

    private List<SimplePostDTO> posts;

}
