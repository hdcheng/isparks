package app.isparks.core.file;


import lombok.Data;

/**
 * @author chenghd
 * @date 2020/9/29
 */
@Data
public class UploadResult {

    private String name;

    private String fileType;

    private String mediaType;

    /**
     * 位置
     */
    private String position;

}
