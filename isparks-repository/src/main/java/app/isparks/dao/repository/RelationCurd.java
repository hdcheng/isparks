package app.isparks.dao.repository;

import app.isparks.core.pojo.entity.Relation;
import app.isparks.core.pojo.enums.EntityType;

import java.util.List;

public abstract class RelationCurd  {

    /**
     * 新建数据
     */
    public abstract Relation insert(Relation relation);

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

    /**
     * 根据关联类型选择ids
     */
    public abstract List<String> selectToIdsByFromType(EntityType fromType);

    /**
     * 根据关联类型选择id
     */
    public abstract List<String> selectFromIdsByToType(EntityType toType);

    /**
     * 根据关联 ids 选择 ids
     */
    public abstract List<String> selectToIdsByFromIds(List<String> fromIds);

    /**
     * 根据关联 ids 选择 ids
     */
    public abstract List<String> selectToIdsByFromIds(List<String> fromIds,EntityType toType);

    /**
     * 根据关联 ids 选择 Relations
     */
    public abstract List<Relation> selectToByFromIdsAndToType(List<String> fromIds, EntityType toType);

    /**
     * 根据关联 ids 选择 ids
     */
    public abstract List<String> selectFromIdsByToIds(List<String> toIds);

    /**
     * 根据关联 ids 选择 ids
     */
    public abstract List<String> selectFromIdsByToIds(List<String> toIds,EntityType fromType);

    /**
     * 根据关联 ids 选择 Relations
     */
    public abstract List<Relation> selectFromByToIdsAndFromType(List<String> toIds, EntityType fromType);

    /**
     * 根据类型删除
     */
    public abstract int deleteByFromType(EntityType fromType);

    /**
     * 根据ids删除
     */
    public abstract int deleteByFromIds(List<String> fromIds);

    /**
     * 根据类型删除
     */
    public abstract int deleteByToType(EntityType toType);

    /**
     * 根据ids删除
     */
    public abstract int deleteByToIds(List<String> toIds);

}
