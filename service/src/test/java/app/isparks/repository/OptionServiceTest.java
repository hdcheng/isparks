package app.isparks.repository;

import app.isparks.core.framework.ISparksApplication;
import app.isparks.core.pojo.enums.SystemProperties;
import app.isparks.core.service.IOptionService;
import app.isparks.core.service.ISysService;
import app.isparks.service.ServiceBoot;
import app.isparks.service.config.ServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/13
 */
public class OptionServiceTest {


    static ApplicationContext applicationContext = null;

    @Before
    public void before(){
        ISparksApplication.instance().register("service",new ServiceBoot());

        ISparksApplication.run();

        applicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
    }

    @Test
    public void test4() {
        IOptionService optionService = applicationContext.getBean(IOptionService.class);
        optionService.getOptionByKey("123");
    }

        @Test
    public void test3(){
        IOptionService optionService = applicationContext.getBean(IOptionService.class);
        boolean b = optionService.connectable();
        System.out.println(b);
    }

    @Test
    public void test2(){
       ISysService sysService = applicationContext.getBean(ISysService.class);
       boolean isInstalled = sysService.isInstalled();
       System.out.println(isInstalled);
    }

    @Test
    public void test1(){

        //applicationContext.getBean()
        IOptionService optionService = applicationContext.getBean(IOptionService.class);
        ISysService sysService = applicationContext.getBean(ISysService.class);

        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("sex","男生");
        map.put("address","中国");
        map.put(SystemProperties.IS_INSTALLED.getKey(),String.valueOf(true));
        sysService.createConfig(map,true);
    }


}
