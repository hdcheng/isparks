package app.isparks.core.config;

import app.isparks.core.pojo.entity.Option;
import app.isparks.core.pojo.param.OptionParam;
import app.isparks.core.util.BeanUtils;
import org.junit.Test;

/**
 * 工具包测试
 *
 * @author： chenghd
 * @date： 2021/1/20
 */
public class UtilsTest {



    @Test
    public void run1(){
        Option option = new Option();
        option.setId("123123123");
        option.setType(1);
        option.setKey("name");
        option.setValue("zhangsan");

        OptionParam param = new OptionParam();
        param.setValue("lisi");
        param.setType(333);

        BeanUtils.updateProperties(param,option);
        System.out.println(option.getValue());
    }


}
