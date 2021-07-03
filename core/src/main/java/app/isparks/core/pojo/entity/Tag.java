package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * @author chenghd
 * @date 2020/7/24
 */
public class Tag extends BaseEntity {

    /**
     * name
     */
    private String name;

    /**
     * description
     */
    private String description;

    public Tag withName(String name) {
        this.name = name;
        return this;
    }

    public Tag withDescription(String description) {
        this.description = description;
        return this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
