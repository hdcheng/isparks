package app.isparks.web.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * @author chenghd
 * @date 2020/9/30
 */
@Configuration
public class HttpConfig {


    /**
     * 配置文件上传大小
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件上传最大值 20 MB
        factory.setMaxFileSize(DataSize.of(20, DataUnit.MEGABYTES));
        // 所有文件上传总最大值 20 MB
        factory.setMaxRequestSize(DataSize.of(20, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

}
