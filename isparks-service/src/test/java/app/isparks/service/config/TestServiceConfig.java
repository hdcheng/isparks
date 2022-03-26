package app.isparks.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"app.isparks.service","app.isparks.dao"})
public class TestServiceConfig {
}
