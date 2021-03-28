package app.isparks.repository;

import app.isparks.core.framework.ISparksApplication;
import app.isparks.core.pojo.dto.LinkDTO;
import app.isparks.core.pojo.entity.Link;
import app.isparks.core.pojo.enums.LinkType;
import app.isparks.core.service.ILinkService;
import app.isparks.service.ServiceBoot;
import app.isparks.service.config.ServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/3/15
 */
public class LinkServiceTest {

    static ApplicationContext applicationContext = null;

    @Before
    public void before(){
        ISparksApplication.instance().register("service",new ServiceBoot());

        ISparksApplication.run();

        applicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
    }

    @Test
    public void test1(){
        ILinkService linkService = applicationContext.getBean(ILinkService.class);

        System.out.println("123");

    }

}
