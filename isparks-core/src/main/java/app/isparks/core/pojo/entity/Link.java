package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * Link
 *
 * @author chenghd
 * @date 2020/7/22
 */
public class Link extends BaseEntity {

    /**
     * link name
     */
    private String name;

    /**
     * link website address
     */
    private String url;

    /**
     * logo
     */
    private String logo;

    /**
     * 链接类型
     */
    private Integer type;

    public Link withName(String name) {
        this.name = name;
        return this;
    }

    public Link withUrl(String url) {
        this.url = url;
        return this;
    }

    public Link withLogo(String logo) {
        this.logo = logo;
        return this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
