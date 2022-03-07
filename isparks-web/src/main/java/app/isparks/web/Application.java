package app.isparks.web;

import app.isparks.core.config.ISparksProperties;
import app.isparks.core.framework.ISparksApplication;
import app.isparks.plugin.PluginBoot;
import app.isparks.service.ServiceBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

/**
 * @author chenghd
 * @date 2020/9/21
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "app.isparks")
@EnableAsync(proxyTargetClass = true)
public class Application {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        Arrays.asList(args).forEach((arg) -> System.out.println(arg) );

        ISparksApplication
                .instance()
                .register("service",new ServiceBoot(args))
                .register("plugin",new PluginBoot(args))
                .register("web",new WebBoot(args));

        // start spring boot
        applicationContext = SpringApplication.run(Application.class, args);

        // start i sparks application
        ISparksApplication.run(applicationContext,args);


        System.out.println("home path:"+ ISparksProperties.USER_HOME);

        System.out.println("123");
    }

}
