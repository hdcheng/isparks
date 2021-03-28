package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/8/3
 */
@Data
public class UserDTO extends BaseDTO {

    /**
     * 签名：如jwt签名
     */
    private String token;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 登录名
     */
    private String userName;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String avatar;

    public UserDTO withToken(String token) {
        this.token = token;
        return this;
    }

}
