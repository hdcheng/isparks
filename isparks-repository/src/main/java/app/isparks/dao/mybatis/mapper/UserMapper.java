package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * user mapper
 *
 * @author chenghd
 * @date 2020/7/24
 */
@Mapper
public interface UserMapper {
    /**
     * insert
     */
    int insert(User user);

    /**
     * delete
     */
    int deleteById(String userId);

    /**
     * delete
     */
    int deleteBy(User user);

    /**
     * select
     */
    List<User> selectAll();

    /**
     * count
     */
    int count();

    /**
     * count
     */
    int countBy(User user);

    /**
     * 查找
     */
    List<User> selectByCondition(User user);

    /**
     * 更新
     */
    int updateById(User user);
}
