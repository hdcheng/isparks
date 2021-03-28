package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author chenghd
 * @date 2020/8/10
 */
@Data
public class TagParam extends BaseParam {

    /**
     * name
     */
    @NotEmpty(message = "标签名不能为空")
    private String name;

    /**
     * description
     */
    private String description;
}
