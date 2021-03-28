package app.isparks.addons.blog.dao;

import app.isparks.addons.blog.entity.PostAttach;
import app.isparks.core.repository.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author： chenghd
 * @date： 2021/3/21
 */
public interface PostAttachMapper extends BaseMapper<PostAttach> {

    /**
     * 插入数据
     */
    int insert(PostAttach pa);

    /**
     * 根据ID删除
     */
    int delete(String id);

    /**
     * 根据post查询数据
     */
    PostAttach selectByPost(String postId);

    /**
     * 根据 createtime分页查询
     */
    List<PostAttach> selectSortByTime(Long ctime);

    /**
     * 根据 visits 分页查询
     */
    List<PostAttach> selectSortByVisits(Long ctime);

    /**
     * 根据 likes 分页查询
     */
    List<PostAttach> selectSortByLikes(Long ctime);

    /**
     * 统计
     */
    int countByPost(String postId);

    /**
     * 更新浏览量
     */
    int updateVisits(@Param("postId") String postId,@Param("visits") long visits,@Param("ctime")long ctime);

    /**
     * 更新收藏量
     */
    int updateLikes(@Param("postId") String postId,@Param("likes") long likes,@Param("ctime")long ctime);


}
