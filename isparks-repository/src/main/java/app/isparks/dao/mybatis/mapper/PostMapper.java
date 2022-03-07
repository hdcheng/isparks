package app.isparks.dao.mybatis.mapper;


import app.isparks.core.pojo.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/4
 */
@Mapper
public interface PostMapper {

    /**
     * 新增
     */
    int insert(Post post);

    /**
     * 删
     */
    int deleteById(String id);

    int deleteByCond(Post post);

    /**
     * 更新
     */
    int update(Post post);

    /**
     * 查找所有数据
     */
    List<Post> selectAll();

    /**
     *
     */
    List<Post> selectByCond(Post post);

    /**
     * 根据ID查询
     */
    Post selectById(String id);

    /**
     * 统计
     */
    int countByCond(Post post);

}
