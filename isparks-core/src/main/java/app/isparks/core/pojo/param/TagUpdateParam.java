package app.isparks.core.pojo.param;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TagUpdateParam extends TagParam {

    @NotEmpty(message = "标签 ID 不能为空")
    private String id;

}
