package app.isparks.dao.mybatis.mapper;


import app.isparks.core.pojo.entity.relation.PostCategoryRL;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/11
 */
@Mapper
public interface PostCategoryRLMapper {

    /**
     * 新增
     */
    int insert(PostCategoryRL rl);

    /**
     * 删除
     */
    int deleteByPost(String postId);

    int deleteByCategory(String categoryId);

    int deleteByCond(PostCategoryRL rl);

    /**
     * 查找
     */
    List<PostCategoryRL> selectByPost(String postId);

    List<PostCategoryRL> selectByCategory(String categoryId);

    List<PostCategoryRL> selectByCond(PostCategoryRL rl);

    int updateById(PostCategoryRL rl);

    long countByCond(PostCategoryRL rl);
}
