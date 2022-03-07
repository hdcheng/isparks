package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;

/**
 * Category
 *
 * @author chenghd
 * @date 2020/7/22
 */
public class Category extends BaseEntity {

    /**
     * category name
     */
    private String name;

    /**
     * category description
     */
    private String description;

    /**
     * parent id
     */
    private String parentId;

    public Category withName(String name) {
        this.name = name;
        return this;
    }

    public Category withParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public Category withDescription(String description) {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
