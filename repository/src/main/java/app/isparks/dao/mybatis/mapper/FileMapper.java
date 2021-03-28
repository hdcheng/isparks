package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.FFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/9/29
 */
@Mapper
public interface FileMapper {

    /**
     * save 增
     */
    int insert(FFile FFile);

    /**
     * delete 删
     */
    int delete(String id);


    int deleteByCond(FFile fFile);

    /**
     * update 改
     */
    int updateById(FFile f);

    /**
     * select 查
     */
    List<FFile> selectByCond(FFile f);

    /**
     * 统计
     */
    long countByCond(FFile f);


}
