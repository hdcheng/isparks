package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * Category
 *
 * @author chenghd
 * @date 2020/7/22
 */
@Data
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
    // getter and setter


    // with
    public Category withParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public Category withDescription(String description) {
        this.description = description;
        return this;
    }
}
