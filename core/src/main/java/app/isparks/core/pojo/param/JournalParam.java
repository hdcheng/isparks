package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author chenghd
 * @date 2020/8/16
 */
@Data
public class JournalParam extends BaseParam {

    @NotEmpty(message = "随笔内容不能为空")
    private String content;

}
