package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * @author chenghd
 * @date 2020/7/24
 */
public class Log extends BaseEntity {

    /**
     * log type/operation type see com.dbwos.fence.pojo.enums.LogType
     */
    private String type;

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
        this.type = type;
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
