package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.BaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @author chenghd
 * @date 2020/8/16
 */
@Setter
@Getter
@ToString
public class JournalParam extends BaseParam {

    private String content;

}
