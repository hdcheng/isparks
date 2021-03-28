package app.isparks.core.pojo.entity;


import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * User
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Data
public class User extends BaseEntity {


    /**
     * nickName
     */
    private String nickName;

    /**
     * userName
     */
    private String userName;

    /**
     * mail
     */
    private String email;

    /**
     * password
     */
    private String password;

    /**
     * avatar
     */
    private String avatar;


    public User withNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public User withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User withEmail(String mail) {
        this.email = mail;
        return this;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public User withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

}
