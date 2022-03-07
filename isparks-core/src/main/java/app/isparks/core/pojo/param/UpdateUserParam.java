package app.isparks.core.pojo.param;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author： chenghd
 * @date： 2021/3/13
 */
@Data
public class UpdateUserParam {

    @NotEmpty(message = "user id不能为空")
    private String id;

    /**
     * 显示名
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * avatar
     */
    private String avatar;

}
