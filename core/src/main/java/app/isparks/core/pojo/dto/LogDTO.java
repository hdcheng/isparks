package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * @author chenghd
 * @date 2020/8/16
 */
@Data
public class LogDTO extends BaseDTO {

    /**
     * type
     */
    private String type;

    /**
     * content
     */
    private String content;

    /**
     * ip
     */
    private String ip;

    /**
     * date
     */
    private String date;


}
