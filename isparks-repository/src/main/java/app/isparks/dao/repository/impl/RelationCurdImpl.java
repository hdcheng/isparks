package app.isparks.dao.repository.impl;

import app.isparks.core.exception.RepositoryException;
import app.isparks.core.pojo.entity.Relation;
import app.isparks.core.pojo.enums.EntityType;
import app.isparks.core.util.IdUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.dao.mybatis.mapper.RelationMapper;
import app.isparks.dao.repository.RelationCurd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
        if(relation == null || StringUtils.hasEmpty(relation.getFromId(),relation.getToId()) || relation.getFromEntity() == null || relation.getToEntity() == null){
            throw new RepositoryException("relation invalid");
        }
        relationMapper.insert(relation);
        return relation;
    }

    @Override
    public long count(EntityType fromType, EntityType toType) {
        if(fromType == null || toType == null){
            log.warn("types must not be null.");
            return 0;
        }
        return relationMapper.countByTypes(fromType.name(),toType.name());
    }

    @Override
    public long countByFromType(EntityType fromType) {
        if(fromType == null ){
            log.warn("fromType must not be null.");
            return 0;
        }
        return relationMapper.countByFromType(fromType.name());
    }

    @Override
    public long countByToType(EntityType toType) {
        if(toType == null){
            log.warn("toType must not be null.");
            return 0;
        }
        return relationMapper.countByToType(toType.name());
    }

    @Override
    public List<String> selectToIdsByFromType(EntityType fromType) {
        if(fromType == null ){
            log.warn("fromType must not be null.");
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectToIdsByFromType(fromType.name());
    }

    @Override
    public List<String> selectFromIdsByToType(EntityType toType) {
        if(toType == null){
            log.warn("toType must not be null.");
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectFromIdsByToType(toType.name());
    }

    @Override
    public List<String> selectToIdsByFromIds(List<String> fromIds) {
        if(fromIds == null || fromIds.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectToIdsByFromIds(fromIds);
    }

    @Override
    public List<String> selectToIdsByFromIds(List<String> fromIds, EntityType toType) {
        if(fromIds == null || fromIds.isEmpty() || toType == null){
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectToIdsByFromIdsAndToType(fromIds,toType.name());
    }

    @Override
    public List<Relation> selectToByFromIdsAndToType(List<String> fromIds, EntityType toType) {
        if(fromIds == null || fromIds.isEmpty() || toType == null){
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectToByFromIdsAndToType(fromIds,toType.name());
    }

    @Override
    public List<Relation> selectFromByToIdsAndFromType(List<String> toIds, EntityType fromType) {
        if(toIds == null || toIds.isEmpty() || fromType == null){
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectFromByToIdsAndFromType(toIds,fromType.name());
    }

    @Override
    public List<String> selectFromIdsByToIds(List<String> toIds, EntityType fromType) {
        if(toIds == null || toIds.isEmpty() || fromType == null){
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectFromIdsByToIdsAndFromType(toIds,fromType.name());
    }



    @Override
    public List<String> selectFromIdsByToIds(List<String> toIds) {
        if(toIds == null || toIds.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return relationMapper.selectFromIdsByToIds(toIds);
    }

    @Override
    public int deleteByFromType(EntityType fromType) {
        if(fromType == null ){
            log.warn("fromType must not be null.");
            return 0;
        }
        return relationMapper.deleteByFromType(fromType.name());
    }

    @Override
    public int deleteByFromIds(List<String> fromIds) {
        if(fromIds == null || fromIds.isEmpty()){
            return 0;
        }
        return relationMapper.deleteByFromIds(fromIds);
    }

    @Override
    public int deleteByToType(EntityType toType) {
        if(toType == null){
            log.warn("toType must not be null.");
            return 0;
        }
        return relationMapper.deleteByToType(toType.name());
    }

    @Override
    public int deleteByToIds(List<String> toIds) {
        if(toIds == null || toIds.isEmpty()){
            return 0;
        }
        return relationMapper.deleteByToIds(toIds);
    }
}
