package app.isparks.dao.config;


import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 *
 * @author chenghd
 * @date 2020/10/9
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("app.isparks.dao.mybatis.mapper")
public class MyBatisConfig {

    /**
     * 配置事务
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactoryBean mybatisSqlSession(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // mybatis 配置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);  // 开启二级缓存
        configuration.setLazyLoadingEnabled(true);  // 懒加载

        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations());

        // 设置page helper分页插件
        PageInterceptor[] pageInterceptors = new PageInterceptor[]{pageInterceptor()};
        sqlSessionFactoryBean.setPlugins(pageInterceptors);

        return sqlSessionFactoryBean;
    }

    public PageInterceptor pageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();

        Properties properties = new Properties();
        properties.setProperty("reasonable","true");
        pageInterceptor.setProperties(properties);

        Properties properties1 = new Properties();
        properties1.setProperty("autoRuntimeDialect","true");
        pageInterceptor.setProperties(properties1);

        Properties properties2 = new Properties();
        properties2.setProperty("helperDialect","H2");  // 默认数据库类型
        pageInterceptor.setProperties(properties2);

        return pageInterceptor;
    }


    public Resource[] resolveMapperLocations(){
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add("classpath*:mapper/*.xml");
        List<Resource> resources = new ArrayList();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }




}
