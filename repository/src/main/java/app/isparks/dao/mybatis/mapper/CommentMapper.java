package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 插入
    int insert(Comment c);

    // 根据条件删除
    int deleteBy(Comment c);

    // 根据条件查找
    List<Comment> selectByCond(Comment c);

    // 根据条件统计数量
    int countBy(Comment c);

    // 根据id更新
    int updateById(Comment c);

}
