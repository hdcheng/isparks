package app.isparks.dao.mybatis.mapper;


import app.isparks.core.pojo.entity.Journal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/8/16
 */
@Mapper
public interface JournalMapper {

    /**
     * save 增
     */
    int insert(Journal j);

    /**
     * delete 删
     */
    int deleteById(String id);

    int deleteByCond(Journal journal);

    /**
     * update 改
     */
    int updateById(Journal journal);

    /**
     * 查询
     */
    List<Journal> selectAll();

    /**
     * select 查
     */
    List<Journal> selectByCond(Journal journal);

    /**
     * count 统计数量
     */
    int countByCond(Journal j);





}
