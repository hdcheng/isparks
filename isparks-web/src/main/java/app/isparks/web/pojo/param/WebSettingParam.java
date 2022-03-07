package app.isparks.web.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author： chenghd
 * @date： 2021/2/4
 */
@Data
public class WebSettingParam {

    @NotEmpty(message = "name 不能为空")
    private String name;

    @NotEmpty(message = "domain 不能为空")
    private String domain;

    @NotEmpty(message = "description 不能为空")
    private String description;

    @NotEmpty(message = "copy 不能为空")
    private String copy;

    @NotEmpty(message = "log text 不能为空")
    private String logText;

    @NotEmpty(message = "log不能为空")
    private String logo;
}
