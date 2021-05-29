package app.isparks.addons.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("app.isparks.addons.**.mapper")
public class AddonsConfig {



}
