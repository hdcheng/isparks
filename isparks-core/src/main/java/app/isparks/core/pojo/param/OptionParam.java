package app.isparks.core.pojo.param;

import app.isparks.core.pojo.base.InputConverter;
import app.isparks.core.pojo.entity.Option;
import lombok.Data;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/13
 */
@Data
public class OptionParam implements InputConverter<Option> {

    private String key;

    private String value;

    private Integer type;

}
