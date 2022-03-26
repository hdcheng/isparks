package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.Relation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关联表
 */
@Mapper
public interface RelationMapper {

    /**
     * 插入
     */
    int insert(Relation relation);

    /**
     * 统计
     */
    long countByTypes(@Param("fromType") String fromType,@Param("toType") String toType);

    /**
     * 统计
     */
    long countByFromType(@Param("fromType") String fromType);

    /**
     * 统计
     */
    long countByToType(@Param("toType") String toType);

    /**
     * 查询id
     */
    List<String> selectToIdsByFromType(@Param("fromType") String fromType);

    /**
     * 查询id
     */
    List<String> selectFromIdsByToType(@Param("toType") String toType);

    /**
     * 查询id
     */
    List<String> selectToIdsByFromIds(@Param("fromIds") List<String> fromIds);

    /**
     * 查询id
     */
    List<String> selectToIdsByFromIdsAndToType(@Param("fromIds") List<String> fromIds,@Param("toType") String toType);

    /**
     * 查询 relation
     */
    List<Relation> selectToByFromIdsAndToType(@Param("fromIds") List<String> fromIds, @Param("toType") String toType);

    /**
     * 查询id
     */
    List<String> selectFromIdsByToIds(@Param("toIds") List<String> toIds);

    /**
     * 查询id
     */
    List<String> selectFromIdsByToIdsAndFromType(@Param("toIds") List<String> toIds,@Param("toType") String fromType);

    /**
     * 查询 relation
     */
    List<Relation> selectFromByToIdsAndFromType(@Param("toIds") List<String> toIds, @Param("fromType") String fromType);

    /**
     * 根据id删除
     */
    int deleteByFromType(@Param("fromType") String fromType);

    /**
     * 根据id删除
     */
    int deleteByFromIds(@Param("fromIds") List<String> fromIds);

    /**
     * 根据id删除
     */
    int deleteByToType(@Param("toType") String toType);

    /**
     * 根据id删除
     */
    int deleteByToIds(@Param("toIds") List<String> toIds);
}
