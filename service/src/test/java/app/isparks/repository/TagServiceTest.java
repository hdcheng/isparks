package app.isparks.repository;

import app.isparks.core.config.DBConfig;
import app.isparks.core.pojo.dto.TagDTO;
import app.isparks.core.pojo.page.PageData;
import app.isparks.core.service.ITagService;
import app.isparks.dao.dialect.enums.Database;
import app.isparks.service.config.ServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * xxx.
 *
 * @author： chenghd
 * @date： 2021/2/28
 */
public class TagServiceTest {

    static ApplicationContext applicationContext = null;

    static ITagService tagService;

    @Before
    public void before(){
        DBConfig.update(Database.H2,"127.0.0.1","8082","sa","");

        applicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);

        tagService = applicationContext.getBean(ITagService.class);
    }

    @Test
    public void test1(){

        PageData<TagDTO> pd = tagService.page(1,100);

        List<String> names = new ArrayList<>();
        names.add("tag1");
        names.add("tag2");
        names.add("tag3");
        names.add("tag测试");

        List<TagDTO> tds = tagService.createTagsByNamesIfAbsent(names);

        PageData<TagDTO> pd1 = tagService.page(1,100);

        System.out.println("1");
    }

}
