package app.isparks.core.pojo.entity;


import app.isparks.core.pojo.enums.EntityType;

/**
 * 用于标识实体类的数据类型
 */
public class Type {

    /**
     * 实体类类型
     */
    private EntityType entityType;

    /**
     * 实体类数据的 id
     */
    private String dataId;

    /**
     * 字典项值
     */
    private int value;


    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
