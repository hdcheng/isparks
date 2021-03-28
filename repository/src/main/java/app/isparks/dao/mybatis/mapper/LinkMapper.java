package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.FFile;
import app.isparks.core.pojo.entity.Link;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author： chenghd
 * @date： 2021/3/13
 */
@Mapper
public interface LinkMapper {

    /**
     * save 增
     */
    int insert(Link link);

    /**
     * delete 删
     */
    int delete(String id);


    int deleteByCond(Link link);

    /**
     * update 改
     */
    int updateById(Link link);

    /**
     * select 查
     */
    List<Link> selectByCond(Link link);

    /**
     * 根据url查找数据
     */
    Link selectByUrl(String url);

    /**
     * 统计
     */
    long countByCond(Link link);


}
