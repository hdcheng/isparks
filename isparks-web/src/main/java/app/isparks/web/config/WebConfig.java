package app.isparks.web.config;

import app.isparks.core.config.ISparksProperties;
import app.isparks.core.util.FileUtils;
import app.isparks.core.service.IUserService;
import app.isparks.service.impl.AbstractCacheService;
import app.isparks.service.impl.CacheServiceImpl;
import app.isparks.service.impl.UserServiceImpl;
import app.isparks.web.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * mvc 配置
 *
 * @author chenghd
 * @date 2020/7/29
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Value("${isparks.jwt.open:true}")
    private Boolean JWT_OPEN = true;

    private AbstractCacheService cacheService;

    private IUserService userService;

    public WebConfig(UserServiceImpl userService, CacheServiceImpl cacheService){
        this.userService = userService;
        this.cacheService = cacheService;
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置 JWT 拦截器
        addJwtInterceptors(registry);
    }

    /**
     * 配置跨域请求
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
        .maxAge(3600)
        .allowCredentials(false);
    }

    /**
     * 配置 jwt 权限验证拦截器
     */
    private void addJwtInterceptors(InterceptorRegistry registry) {
        //拦截的地址
        String[] jwtPath = {"/*/admin/**", "/admin/**","/api/sys/**"};
        //不拦截的地址
        String[] jwtExcludePath = {"/api/admin/authenticate",
                "/admin/login", "/admin/install","/api/admin/install",
                "/api/sys/installed"};

        registry.addInterceptor(new JwtInterceptor(userService,JWT_OPEN))
                .addPathPatterns(jwtPath)
                .excludePathPatterns(jwtExcludePath);
    }

    /**
     * 资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String homeStaticDir = FileUtils.parseToUrl(ISparksProperties.RESOURCES_FILE_PATH);
        String themeStaticDir = FileUtils.parseToUrl(ISparksProperties.THEME_STATIC_FILE_PATH);

        registry.addResourceHandler(WebProperties.STATIC_REQUEST_MAP)
                .addResourceLocations("classpath:/static/",homeStaticDir,themeStaticDir);

        //api doc 是否展示
        if (WebProperties.docAvailable) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

}
