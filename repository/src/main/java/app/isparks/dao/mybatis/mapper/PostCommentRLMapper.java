package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.relation.PostCommentRL;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostCommentRLMapper {

    // 插入
    int insert(PostCommentRL rl);

    // 根据 post id 删除
    int deleteByPost(String postId);

    // 根据条件删除
    int deleteBy(PostCommentRL rl);

    // 根据 comment id 删除
    int deleteByComment(String commentId);

    // 根据条件查找
    List<PostCommentRL> selectByCond(PostCommentRL rl);

    // 根据 post 查找
    List<PostCommentRL> selectByPost(String postId);

    // 统计数量
    int countByPost(String postId);

    // 根据id更新
    int updateById(PostCommentRL rl);

    // 统计数量
    int countBy(PostCommentRL rl);
}
