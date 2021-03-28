package app.isparks.web.config;

import app.isparks.core.config.ISparksProperties;
import app.isparks.core.dao.cache.AbstractCacheStore;
import app.isparks.core.file.util.FileUtils;
import app.isparks.core.service.IUserService;
import app.isparks.web.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * mvc 配置
 *
 * @author chenghd
 * @date 2020/7/29
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private AbstractCacheStore cacheStore;

    @Autowired
    private IUserService userService;

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置 JWT 拦截器
        addJwtInterceptors(registry);
    }

    /**
     * 配置 jwt 权限验证拦截器
     */
    private void addJwtInterceptors(InterceptorRegistry registry) {
        //拦截的地址
        String[] jwtPath = {"/api/admin/**", "/admin/**","/api/sys/**"};
        //不拦截的地址
        String[] jwtExcludePath = {"/api/admin/authenticate",
                "/admin/login", "/admin/install","/api/admin/install",
                "/api/sys/installed"};
        registry.addInterceptor(new JwtInterceptor(cacheStore,userService))
                .addPathPatterns(jwtPath)
                .excludePathPatterns(jwtExcludePath);
    }

    /**
     * 资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String staticDir = FileUtils.parseToUrl(WebProperties.templatePrefix) +" static";

        String homeStaticDir = FileUtils.parseToUrl(ISparksProperties.RESOURCES_FILE_PATH);

        registry.addResourceHandler(WebProperties.STATIC_REQUEST_MAP)
                .addResourceLocations("classpath:/static/")
                //.addResourceLocations("classpath:/templates/static/")
                .addResourceLocations(staticDir)
                .addResourceLocations(homeStaticDir);

        //registry.addResourceHandler(FenceWebProperties.STATIC_REQUEST_MAP).addResourceLocations(FileUtils.parseToUrl(workDir));

        //api doc 是否展示
        if (WebProperties.docAvailable) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }



}
