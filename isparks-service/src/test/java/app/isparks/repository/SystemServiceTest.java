package app.isparks.repository;

import app.isparks.core.service.ISysService;
import app.isparks.service.config.ServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author： chenghd
 * @date： 2021/1/23
 */
public class SystemServiceTest {

    static ApplicationContext applicationContext = null;

    @Before
    public void before(){
        applicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
    }


    @Test
    public void test2(){
        ISysService sysService = applicationContext.getBean(ISysService.class);
        sysService.syncConfig();
    }

    @Test
    public void test1(){

    }

}
