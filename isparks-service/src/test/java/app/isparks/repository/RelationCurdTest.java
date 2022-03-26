package app.isparks.repository;

import app.isparks.core.framework.ISparksApplication;
import app.isparks.core.pojo.entity.Relation;
import app.isparks.core.pojo.enums.EntityType;
import app.isparks.core.service.IRelationService;
import app.isparks.dao.repository.RelationCurd;
import app.isparks.service.ServiceBoot;
import app.isparks.service.config.ServiceConfig;
import app.isparks.service.config.TestServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RelationCurdTest {

    static ApplicationContext applicationContext = null;

    @Before
    public void before(){
        ISparksApplication.instance().register("service",new ServiceBoot());

        applicationContext = new AnnotationConfigApplicationContext(TestServiceConfig.class);
    }

    @Test
    public void test1(){
        IRelationService relationService = applicationContext.getBean(IRelationService.class);
        Relation relation = new Relation();
        relation.setFromEntity(EntityType.POST);
        relation.setFromId("123");
        relation.setToEntity(EntityType.CATEGORY);
        relation.setToId("456");
        relationService.create(relation);

    }

}
