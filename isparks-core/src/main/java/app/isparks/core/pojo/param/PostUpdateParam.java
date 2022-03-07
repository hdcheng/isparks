package app.isparks.core.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostUpdateParam extends PostParam {

    @NotEmpty(message = "文章 ID 不能为空")
    private String id;

}
