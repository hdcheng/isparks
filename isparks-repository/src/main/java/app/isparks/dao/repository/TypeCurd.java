package app.isparks.dao.repository;

import app.isparks.core.pojo.entity.Type;
import app.isparks.core.pojo.enums.EntityType;

import java.util.List;

public abstract class TypeCurd {

    /**
     * 新建数据
     */
    public abstract Type insert(Type type);

    /**
     * 查找指定类型的 id
     */
    public abstract List<String> selectIdsByEntityTypeAndValue(EntityType entityType , int value);

    /**
     * 删除
     */
    public abstract int deleteByIds(List<String> ids);

    /**
     * 删除
     */
    public abstract int deleteByEntityType(EntityType entityType);

    /**
     * 删除
     */
    public abstract int deleteByValue(int value);

}
