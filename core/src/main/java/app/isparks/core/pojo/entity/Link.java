package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * Link
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Data
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

}
