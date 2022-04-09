package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * @author chenghd
 * @date 2020/7/24
 */
public class Log extends BaseEntity {

    /**
     * log type/operation type
     */
    private String types;

    /**
     * content/description
     */
    private String content;

    /**
     * ip
     */
    private String ip;

    /**
     * date
     */
    private String date;

    public Log withType(String type) {
        this.types = type;
        return this;
    }

    public Log withContent(String content) {
        this.content = content;
        return this;
    }

    public Log withIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Log withDate(String date) {
        this.date = date;
        return this;
    }


    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
