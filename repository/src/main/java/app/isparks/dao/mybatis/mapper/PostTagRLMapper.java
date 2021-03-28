package app.isparks.dao.mybatis.mapper;


import app.isparks.core.pojo.entity.relation.PostTagRL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/11
 */
@Mapper
public interface PostTagRLMapper {

    /**
     * 新增
     */
    int insert(PostTagRL rl);


    /**
     * 删除
     */
    int deleteByPost(String postId);

    int deleteByTag(String tagId);

    int deleteByCond(PostTagRL rl);

    int updateById(PostTagRL rl);

    /**
     * 查找
     */
    List<PostTagRL> selectByPost(@Param("postId") String postId);

    /**
     *
     */
    List<PostTagRL> selectByTag(@Param("tagId") String tagId);

    /**
     *
     */
    PostTagRL selectByPostAndTag(@Param("postId") String postId, @Param("tagId") String tagId);

    /**
     *
     */
    List<PostTagRL> selectByCond(PostTagRL postTagRL);


    long countByCond(PostTagRL rl);

}
