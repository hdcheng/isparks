package app.isparks.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenghd
 * @date 2020/7/26
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket rpcApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(rpcApiInfo())
                .select()
                //controller包路径
                .apis(RequestHandlerSelectors.basePackage("app.isparks.web.controller.api"))
                .paths(PathSelectors.any())
                .build();
        docket.groupName("RPC");
        docket.host("localhost:8174");
        return docket;
    }


    private ApiInfo rpcApiInfo() {
        return new ApiInfoBuilder()
                .title("ISparks RPC API Document")
                .version("v1")
                .description("ISparks 接口参考文档")
                .build();
    }

    @Bean
    public Docket restApi(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(restApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("app.isparks.web.controller.rest"))
                .paths(PathSelectors.any())
                .build();
        docket.groupName("REST");
        docket.host("127.0.0.1:8174");

        return docket;
    }


    private ApiInfo restApiInfo() {
        return new ApiInfoBuilder()
                .title("ISparks Rest API Document")
                .version("v1")
                .description("ISparks 接口参考文档")
                .build();
    }
}
