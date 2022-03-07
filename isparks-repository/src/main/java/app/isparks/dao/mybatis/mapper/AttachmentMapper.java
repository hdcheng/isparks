package app.isparks.dao.mybatis.mapper;

import app.isparks.core.pojo.entity.Attachment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachmentMapper {

    /**
     * save 增
     */
    int insert(Attachment attachment);

    /**
     * delete 删
     */
    int deleteByCond(Attachment a);

    /**
     * update 改
     */
    int updateById(Attachment attachment);

    /**
     * select 查
     */
    List<Attachment> selectByCond(Attachment attachment);

    /**
     * 统计
     */
    long countByCond(Attachment attachment);

}
