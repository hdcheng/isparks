package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/8/10
 */
@Data
public class TagDTO extends BaseDTO {

    /**
     * name
     */
    private String name;

    /**
     * description
     */
    private String description;

}
