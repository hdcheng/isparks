package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.Option;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper {

    /**
     * save 增
     */
    int insert(Option params);

    /**
     * delete 删
     */
    int deleteByCond(Option o);


    /**
     * update 改
     */
    int updateById(Option params);

    /**
     * select 查
     */
    List<Option> selectByCond(Option params);


    long countByCond(Option o);

}
