package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.Relation;
import app.isparks.core.pojo.enums.EntityType;
import app.isparks.dao.mybatis.mapper.RelationMapper;
import app.isparks.dao.repository.RelationCurd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelationCurdImpl extends RelationCurd {

    private Logger log = LoggerFactory.getLogger(RelationCurdImpl.class);

    private RelationMapper relationMapper;

    public RelationCurdImpl(RelationMapper relationMapper){
        this.relationMapper = relationMapper;
    }

    protected int deleteBy(Relation relation) {
        return 0;
    }

    public Relation insert(Relation relation) {
        return null;
    }

    public List<Relation> selectByCond(Relation relation) {
        return null;
    }

    public Relation updateById(Relation relation) {
        return null;
    }

    public long countBy(Relation relation) {
        return 0;
    }

    @Override
    public long count(EntityType fromType, EntityType toType) {
        return 0;
    }

    @Override
    public long countByFromType(EntityType fromType) {
        return 0;
    }

    @Override
    public long countByToType(EntityType toType) {
        return 0;
    }

    @Override
    public List<String> selectToIdsByFromType(EntityType fromType) {
        return null;
    }

    @Override
    public List<String> selectFromIdsByToType(EntityType toType) {
        return null;
    }

    @Override
    public List<String> selectToIdsByFromIds(List<String> fromIds) {
        return null;
    }

    @Override
    public List<String> selectFromIdsByToIds(List<String> toIds) {
        return null;
    }
}
