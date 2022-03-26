package app.isparks.service.impl;

import app.isparks.core.pojo.base.BaseEntity;
import app.isparks.core.pojo.entity.Relation;
import app.isparks.core.pojo.enums.EntityType;
import app.isparks.core.service.IRelationService;
import app.isparks.core.util.StringUtils;
import app.isparks.dao.repository.RelationCurd;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationServiceImpl implements IRelationService {

    private RelationCurd relationCurd;

    public RelationServiceImpl(RelationCurd relationCurd){
        this.relationCurd = relationCurd;
    }

    @Override
    public Optional<Relation> create(Relation relation) {
        return Optional.ofNullable(relationCurd.insert(relation));
    }

    @Override
    public <F extends BaseEntity, T extends BaseEntity> Optional<Relation> create(F f, T t) {
        EntityType fromType = entityType(f);
        EntityType toType = entityType(t);
        if(fromType == null || toType == null || (StringUtils.hasEmpty(f.getId(),t.getId()))){
            return Optional.empty();
        }
        Relation relation = new Relation();
        relation.setFromId(f.getId());
        relation.setFromEntity(fromType);
        relation.setToId(t.getId());
        relation.setToEntity(toType);
        return create(relation);
    }


    @Override
    public Map<String,List<String>> findToIds(List<String> fromIds , EntityType toType) {
        if(fromIds == null || fromIds.isEmpty() || toType == null){
            return Collections.EMPTY_MAP;
        }
        List<Relation> relations = relationCurd.selectToByFromIdsAndToType(fromIds,toType);
        Map<String,List<String>> res = new HashMap<>();
        for(String fromId : fromIds){
            res.put(fromId,new LinkedList<>());
        }
        List<String> toIds ;
        for(Relation relation : relations){
            toIds = res.get(relation.getFromId());
            if(toIds != null){
                toIds.add(relation.getToId());
            }
        }
        return res;
    }

    @Override
    public Map<String, List<String>> findFromIds(List<String> toIds, EntityType fromType) {
        if(toIds == null || toIds.isEmpty() || fromType == null){
            return Collections.EMPTY_MAP;
        }
        List<Relation> relations = relationCurd.selectFromByToIdsAndFromType(toIds,fromType);
        Map<String,List<String>> res = new HashMap<>();
        for(String toId : toIds){
            res.put(toId,new LinkedList<>());
        }
        List<String> fromIds ;
        for(Relation relation : relations){
            fromIds = res.get(relation.getToId());
            if(fromIds != null){
                fromIds.add(relation.getFromId());
            }
        }
        return res;
    }

    private <T extends BaseEntity> EntityType entityType(T t){
        if(t != null){
            for(EntityType type : EntityType.values()){
                if(type.typeClass().isInstance(t)){
                    return type;
                }
            }
        }
        return null;
    }

    private <T extends BaseEntity> EntityType classType(Class<T> tClass){
        if(tClass != null){
            for(EntityType type : EntityType.values()){
                if(type.typeClass() == tClass){
                    return type;
                }
            }
        }
        return null;
    }

}
