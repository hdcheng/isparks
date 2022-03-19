package app.isparks.core.pojo.entity;


import app.isparks.core.pojo.base.MetaEntity;
import app.isparks.core.pojo.enums.EntityType;

public class Relation extends MetaEntity {

    private EntityType fromEntity;

    private String fromId;

    private EntityType toEntity;

    private String toId;

    public EntityType getFromEntity() {
        return fromEntity;
    }

    public void setFromEntity(EntityType fromEntity) {
        this.fromEntity = fromEntity;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public EntityType getToEntity() {
        return toEntity;
    }

    public void setToEntity(EntityType toEntity) {
        this.toEntity = toEntity;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
