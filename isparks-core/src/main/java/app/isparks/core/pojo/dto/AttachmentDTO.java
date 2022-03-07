package app.isparks.core.pojo.dto;


import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;


/**
 * @author chenghd
 * @date 2020/8/31
 */
@Data
public class AttachmentDTO extends BaseDTO {

    private String id;

    /**
     * 文件名
     */
    private String postId;

    /**
     * 文件数据
     */
    private String fileId;

}
