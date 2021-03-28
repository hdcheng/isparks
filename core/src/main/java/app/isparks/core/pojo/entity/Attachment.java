package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * 附件实体类
 *
 * @author chenghd
 * @date 2020/8/18
 */
@Data
public class Attachment extends BaseEntity {

    /**
     * 关联的帖子
     */
    private String postId;

    /**
     * 文件
     */
    private String fileId;

}
