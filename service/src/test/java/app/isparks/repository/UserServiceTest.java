package app.isparks.repository;

import app.isparks.core.pojo.entity.User;
import app.isparks.service.config.ServiceConfig;
import app.isparks.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/1/19
 */
public class UserServiceTest {

    static ApplicationContext applicationContext = null;

    @Before
    public void before(){
        applicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
    }


    @Test
    public void test1(){
       UserServiceImpl userService = applicationContext.getBean(UserServiceImpl.class);

//       User user = new User();
//       user.setAvatar("1");
//       user.setEmail("eastry@qq.com");
//      user.setUserName("eastry");
//       user.setPassword("123456");
//       userService.insert(user);
        User user = userService.getByEmail("eastry@qq.com").orElse(new User());

        System.out.println(user.getUserName());

    }

}
