package app.isparks.core.pojo.dto;


import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/8/31
 */
@Data
public class LinkDTO extends BaseDTO {

    /**
     * link name
     */
    private String name;

    /**
     * link website address
     */
    private String url;

    /**
     * logo
     */
    private String logo;

}
