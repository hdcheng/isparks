package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

@Data
public class FileDTO extends BaseDTO {

    /**
     * 随机生成文件名
     */
    private String name;

    /**
     * 原文件名
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
