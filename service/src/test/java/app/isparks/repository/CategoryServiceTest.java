package app.isparks.repository;

import app.isparks.core.config.DBConfig;
import app.isparks.core.pojo.enums.LogType;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.pojo.param.CategoryParam;
import app.isparks.core.service.ICategoryService;
import app.isparks.dao.dialect.enums.Database;
import app.isparks.service.config.ServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * @author： chenghd
 * @date： 2021/1/7
 */
public class CategoryServiceTest {

    static ApplicationContext applicationContext = null;

    static ICategoryService categoryService;

    @Before
    public void before(){
        DBConfig.update(Database.H2,"127.0.0.1","8082","sa","");

        applicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);

        categoryService = applicationContext.getBean(ICategoryService.class);
    }

    @Test
    public void test4(){
        LogType[] types = {LogType.UNKNOWN,LogType.FILE_UPLOAD,LogType.MODIFY};
        StringJoiner joiner = new StringJoiner(",","[","]");
        Arrays.stream(types).forEach(type -> {
            joiner.add(type.getDescription());
        });
        System.out.println(joiner.toString());
    }

    @Test
    public void test3(){
        CategoryParam param = new CategoryParam();
        param.setName("test333");
        String id = "3ae4e31c96604b1b889ab6fffeef0b94";
        categoryService.update(id,param);
    }

    @Test
    public void test2(){
       PageData pageData = categoryService.page(1,10);
        System.out.println(pageData.getData());
    }

    @Test
    public void test1(){

        CategoryParam param = new CategoryParam();
        param.setName("test");
        param.setDescription("asdfasdf");

        Optional result = categoryService.create(param);

    }



}
