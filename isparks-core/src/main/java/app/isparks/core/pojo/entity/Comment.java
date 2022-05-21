package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * 评论
 *
 * @author chenghd
 * @date 2020/8/18
 */
public class Comment extends BaseEntity {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人名称
     */
    private String name;

    /**
     * 评论人邮箱
     */
    private String email;

    /**
     * 评论人的网站地址
     */
    private String address;

    /**
     * 评论者ip
     */
    private String ip;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
