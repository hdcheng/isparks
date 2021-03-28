package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import app.isparks.core.pojo.base.InputConverter;
import app.isparks.core.pojo.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 用户参数
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Data
public class UserParam extends BaseParam implements InputConverter<User> {

    /**
     * 显示名
     */
    @NotEmpty(message = "显示昵称不能为空")
    @Size(min = 1, max = 30, message = "昵称长度在 1~30 之间")
    private String nickName;
    /**
     * 登录名
     */
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 5, max = 30, message = "用户名（登录名）长度在 5~30 之间")
    private String userName;

    /**
     * 邮箱
     */
    @NotEmpty(message = "email不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不得小于 6 ")
    private String password;

    /**
     * avatar
     */
    private String avatar;

    public UserParam withNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public UserParam withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserParam withEmail(String mail) {
        this.email = mail;
        return this;
    }

    public UserParam withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserParam withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
