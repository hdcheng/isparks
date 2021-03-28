package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


/**
 * 登录参数
 *
 * @author chenghd
 * @date 2020/8/3
 */
@Data
public class LoginParam extends BaseParam {

    @NotEmpty(message = "输入邮箱/登录名")
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    //验证码
    private String authCode;
}
