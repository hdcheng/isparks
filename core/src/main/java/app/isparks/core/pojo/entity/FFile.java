package app.isparks.core.pojo.entity;

import app.isparks.core.pojo.base.BaseEntity;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/9/29
 */
@Data
public class FFile extends BaseEntity {

    /**
     * 随机生成文件名
     */
    private String name;

    /**
     * 文件描述，一般为原文件名。
     */
    private String origin;

    /**
     * 媒体类型
     */
    private String mediaType;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 储存路径，可能是本地储存路径，或者是一个url路径。
     */
    private String location;

}
