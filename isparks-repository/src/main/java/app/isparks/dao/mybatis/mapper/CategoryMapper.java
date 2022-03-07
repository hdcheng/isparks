package app.isparks.dao.mybatis.mapper;



import app.isparks.core.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/4
 */
@Mapper
public interface CategoryMapper {

    /**
     * 新增数据
     */
    int insert(Category c);

    /**
     * 根据分类名删除数据
     */
    int deleteByName(String name);

    /**
     * delete by id
     */
    int deleteById(String id);

    int deleteByCond(Category c);

    /**
     * update
     */
    int updateById(Category c);

    /**
     * 查找
     */
    List<Category> selectAll();

    Category selectById(String id);

    List<Category> selectByName(String name);

    /**
     *
     */
    List<Category> selectByCond(Category category);

    long countByCond(Category c);

}
