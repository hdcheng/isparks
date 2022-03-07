package app.isparks.core.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author chenghd
 * @date 2020/10/21
 */
@Data
public class CategoryUpdateParam extends CategoryParam {

    @NotEmpty(message = "分类 ID 不能为空")
    private String id;

}
