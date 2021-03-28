package app.isparks.dao.mybatis.mapper;


import app.isparks.core.pojo.entity.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/7/25
 */
@Mapper
public interface LogMapper {
    /**
     * 新增
     */
    int insert(Log log);

    /**
     * 查询
     */
    List<Log> selectAll();

    /**
     *
     */
    List<Log> selectByCond(Log log);

    /**
     * 根据id 删除数据
     */
    int deleteByCond(Log log);


    int updateByCond(Log log);


    long countByCond(Log log);

}
