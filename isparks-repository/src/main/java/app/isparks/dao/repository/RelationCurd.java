package app.isparks.dao.repository;

import app.isparks.core.pojo.enums.EntityType;

import java.util.List;

public abstract class RelationCurd  {


    /**
     * 统计连个类型关联数量
     * @param fromType
     * @param toType
     */
    public abstract long count(EntityType fromType,EntityType toType);

    /**
     * 根据 fromType 统计数据
     * @param fromType
     */
    public abstract long countByFromType(EntityType fromType);

    /**
     * 根据 toType 统计数据
     * @param toType
     */
    public abstract long countByToType(EntityType toType);

    public abstract List<String> selectToIdsByFromType(EntityType fromType);

    public abstract List<String> selectFromIdsByToType(EntityType toType);

    public abstract List<String> selectToIdsByFromIds(List<String> fromIds);

    public abstract List<String> selectFromIdsByToIds(List<String> toIds);
}
