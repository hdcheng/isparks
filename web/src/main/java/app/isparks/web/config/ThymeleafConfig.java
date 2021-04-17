package app.isparks.web.config;

import app.isparks.core.web.property.WebConstant;
import app.isparks.web.service.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Map;

/**
 * @author chenghd
 * @date 2020/8/5
 */
@Configuration
public class ThymeleafConfig {

    private final static String ADMIN_URL_PREFIX = "/" + WebConstant.ADMIN_TEMPLATE_PATH_NAME;


    private ApplicationContext applicationContext;

    @Autowired
    private ThemeServiceImpl themeService;

    public ThymeleafConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 配置视图解析器
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setContentType("text/html");

        return viewResolver;
    }

    /**
     * 模板引擎
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateEngine.setTemplateResolver(templateResolver()); // class 路径

        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver(){

            // 配置多个thymeleaf模板目录
            @Override
            protected ITemplateResource computeTemplateResource(final IEngineConfiguration configuration,
                                                                final String ownerTemplate,
                                                                final String template,
                                                                final String resourceName,
                                                                final String characterEncoding,
                                                                final Map<String, Object> templateResolutionAttributes){

                if(template.startsWith(WebConstant.PLUGIN_TEMPLATE_PATH_NAME)){
                    // plugin 页面路径
                    String filePath = resourceName.replaceFirst(ADMIN_URL_PREFIX,"");
                    return super.computeTemplateResource(configuration,ownerTemplate,template,filePath,characterEncoding,templateResolutionAttributes);
                }

                if(template.startsWith(WebConstant.WEB_TEMPLATE_PATH_NAME) || (ownerTemplate != null && ownerTemplate.startsWith(WebConstant.WEB_TEMPLATE_PATH_NAME))){

                    // web页面路径
                    String filePath ;

                    if(ownerTemplate != null){
                        filePath = resourceName.replace(WebConstant.ADMIN_TEMPLATE_CLASSPATH,WebConstant.WEB_TEMPLATE_CLASSPATH);
                    }else{
                        filePath = resourceName.replaceFirst(ADMIN_URL_PREFIX,"");
                    }
                    filePath = themeService.resolveTheme(filePath);
                    return super.computeTemplateResource(configuration,ownerTemplate,template,filePath,characterEncoding,templateResolutionAttributes);
                }

                return super.computeTemplateResource(configuration,ownerTemplate,template,resourceName,characterEncoding,templateResolutionAttributes);
            }
        };

        templateResolver.setApplicationContext(applicationContext);

        templateResolver.setPrefix("classpath:/admin/");

        templateResolver.setSuffix(WebConstant.THYMELEAF_FILE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("utf-8");

        // 开发环境是false
        templateResolver.setCacheable(WebProperties.cache);

        return templateResolver;
    }



}
