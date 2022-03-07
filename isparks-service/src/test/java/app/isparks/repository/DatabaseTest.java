package app.isparks.repository;

import app.isparks.core.config.DBConfig;
import app.isparks.core.service.ISysService;
import app.isparks.dao.dialect.enums.Database;
import app.isparks.service.config.ServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * 测试.
 *
 * @author： chenghd
 * @date： 2021/1/6
 */
public class DatabaseTest {

    static ApplicationContext applicationContext = null;

    @Before
    public void before(){
        applicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
    }

    @Test
    public void test2(){
        boolean result = Database.H2.toString().equals("h2".toUpperCase());
        System.out.println(result);
    }

    @Test
    public void test1(){
        // 测试pgsql数据库初始化
        ISysService sysService = applicationContext.getBean(ISysService.class);
        DBConfig.setDatabase(Database.POSTGRESQL);
        DBConfig.setUserName("postgres");
        DBConfig.setPassword("123456");
        DBConfig.setIp("192.168.131.130");
        sysService.initDB();
    }


}
