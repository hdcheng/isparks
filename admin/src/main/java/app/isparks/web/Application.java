package app.isparks.web;

import app.isparks.addons.blog.BlogBoot;
import app.isparks.core.config.ISparksProperties;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.plugin.PluginBoot;
import app.isparks.service.ServiceBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

/**
 * @author chenghd
 * @date 2020/9/21
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "app.isparks")
//@ComponentScan(
//        basePackages = "app.isparks",
//        excludeFilters = { @ComponentScan.Filter(type = FilterType.CUSTOM , classes = TypeExcludeFilter.class),
//        @ComponentScan.Filter(type = FilterType.CUSTOM , classes = AutoConfigurationExcludeFilter.class),
//        @ComponentScan.Filter(type = FilterType.REGEX , pattern = {"app.isparks.addons.*"})
//})
@EnableAsync(proxyTargetClass = true)
public class Application {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        Arrays.asList(args).forEach((arg) -> System.out.println(arg) );

        ISparksApplication application = ISparksApplication.instance();

        //application.register("plugin",new PluginBoot(args));

        application.register("service",new ServiceBoot(args));

        application.register("blog",new BlogBoot());

        // start spring boot
        applicationContext = SpringApplication.run(Application.class, args);

        ISparksApplication.run(applicationContext,args);

        System.out.println("home path:"+ ISparksProperties.USER_HOME);

    }

}
