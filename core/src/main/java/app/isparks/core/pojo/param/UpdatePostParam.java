package app.isparks.core.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author： chenghd
 * @date： 2021/2/28
 */
@Data
public class UpdatePostParam extends PostParam{

    @NotEmpty(message = "ID不能为空")
    private String id;


}
