package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/4
 */
@Mapper
public interface TagMapper {

    /**
     * 保存
     */
    int insert(Tag t);

    /**
     * 查找所有标签
     */
    List<Tag> selectAll();

    /**
     *
     */
    List<Tag> selectByName(String name);

    /**
     *
     */
    Tag selectById(String id);

    /**
     *
     */
    List<Tag> selectByIds(@Param("ids") List<String> ids);

    /**
     * 更新
     */
    int updateById(Tag tag);

    /**
     *
     */
    List<Tag> selectByCond(Tag tag);

    /**
     * 删除
     */
    int deleteByName(String name);

    /**
     * 删除
     */
    int deleteById(String id);

    int deleteByCond(Tag t);


    long countByCond(Tag t);

}
