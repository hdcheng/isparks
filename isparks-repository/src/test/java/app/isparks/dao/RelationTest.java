package app.isparks.dao;

import app.isparks.config.TestDaoConfig;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.core.pojo.entity.Relation;
import app.isparks.core.pojo.enums.EntityType;
import app.isparks.dao.config.IDataSourceFactory;
import app.isparks.dao.dialect.enums.Database;
import app.isparks.dao.repository.RelationCurd;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.List;

public class RelationTest {

    static ApplicationContext applicationContext = null;

    @Before
    public void before(){
        ISparksApplication.instance().register("service",new RepositoryBoot());

        applicationContext = new AnnotationConfigApplicationContext(TestDaoConfig.class);

        IDataSourceFactory dataSourceFactory = applicationContext.getBean(IDataSourceFactory.class);
        dataSourceFactory.reload("postgres","123456","jdbc:postgresql://192.168.231.128:5432/isparks", Database.POSTGRESQL);
    }

    @Test
    public void testInsert(){
        RelationCurd relationCurd =applicationContext.getBean(RelationCurd.class);
        Relation relation = new Relation();
        relation.setFromEntity(EntityType.POST);
        relation.setFromId("123");
        relation.setToEntity(EntityType.CATEGORY);
        relation.setToId("456");
        relationCurd.insert(relation);
        System.out.println("123");
    }

    @Test
    public void testCount(){
        RelationCurd relationCurd =applicationContext.getBean(RelationCurd.class);
        long count= relationCurd.count(EntityType.POST,EntityType.TAG);
        System.out.println(count);
    }

    @Test
    public void testFindIds(){
        RelationCurd relationCurd =applicationContext.getBean(RelationCurd.class);

        List<String> ids = relationCurd.selectFromIdsByToType(EntityType.TAG);
        System.out.println("success");
    }

    @Test
    public void testFindIds1(){
        RelationCurd relationCurd =applicationContext.getBean(RelationCurd.class);

        List<String> ids = relationCurd.selectFromIdsByToIds(Collections.singletonList("4561"));
        System.out.println("success");
    }

    @Test
    public void testDelete(){
        RelationCurd relationCurd =applicationContext.getBean(RelationCurd.class);
        int num = relationCurd.deleteByFromIds(Collections.singletonList("123"));
        System.out.println(num);
    }

    @Test
    public void testFindRelation(){
        RelationCurd relationCurd =applicationContext.getBean(RelationCurd.class);
        List<Relation> relations = relationCurd.selectFromByToIdsAndFromType(Collections.singletonList("456"),EntityType.POST);
        System.out.println("123");
    }

}
