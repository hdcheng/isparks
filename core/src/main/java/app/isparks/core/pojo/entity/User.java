package app.isparks.core.pojo.entity;


import app.isparks.core.pojo.base.BaseEntity;

/**
 * User
 *
 * @author chenghd
 * @date 2020/7/22
 */
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


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
