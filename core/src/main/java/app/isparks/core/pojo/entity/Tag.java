package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/7/24
 */
@Data
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

}
