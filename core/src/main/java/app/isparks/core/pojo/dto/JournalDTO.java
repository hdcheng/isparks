package app.isparks.core.pojo.dto;

import app.isparks.core.pojo.base.BaseDTO;
import lombok.Data;

/**
 * 日志/随笔
 *
 * @author chenghd
 * @date 2020/8/16
 */
@Data
public class JournalDTO extends BaseDTO {

    /**
     * 日志/随笔内容
     */
    private String content;

}
