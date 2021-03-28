package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import lombok.Data;

/**
 * @author： chenghd
 * @date： 2021/3/28
 */
@Data
public class LinkParam extends BaseParam {

    private String id;

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

    /**
     * 链接类型
     */
    private Integer type;

}
