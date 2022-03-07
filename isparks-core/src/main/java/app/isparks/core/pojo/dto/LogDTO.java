package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenghd
 * @date 2020/8/16
 */
@Setter
@Getter
@EqualsAndHashCode
public class LogDTO extends BaseDTO {

    /**
     * types
     */
    private String types;

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
