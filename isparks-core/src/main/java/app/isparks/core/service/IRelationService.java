package app.isparks.core.service;

import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.core.pojo.entity.Relation;
import app.isparks.core.pojo.enums.EntityType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IRelationService {

    /**
     * 创建关联对象
     */
    Optional<Relation> create(Relation relation);

    /**
     * 根据两个实体类创建关联对象
     */
    <F extends BaseEntity,T extends BaseEntity> Optional<Relation>  create(F f, T t);

    /**
     *  查找 to ids
     */
    Map<String,List<String>> findToIds(List<String> fromIds , EntityType toType);

    /**
     *  查找 from ids
     */
    Map<String,List<String>> findFromIds(List<String> toIds , EntityType fromType);
}
