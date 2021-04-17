package app.isparks.web.interceptor;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.dao.cache.AbstractCacheStore;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Jwt验证拦截器
 *
 * @author chenghd
 * @date 2020/8/4
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    private AbstractCacheStore cacheStore;

    private IUserService userService;

    public JwtInterceptor(AbstractCacheStore cacheStore,IUserService userService){
        this.cacheStore = cacheStore;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (true)return true;

        String uri = request.getRequestURI();
        if (uri.endsWith("/api/admin/authenticate")) return true;

        String jwtToken = getTokenFromRequest(request);

        if(userService.verifyJwtToken(jwtToken)){
            return true;
        }

        if(!isAsync(request)){
            response.sendRedirect("/admin/login");
        }
        return false;
    }

    // todo：处理cookies禁止的情况
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(ISparksConstant.AUTHORIZATION);

        if(StringUtils.isEmpty(token)){
            // 从cookie中获取
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                int len = cookies.length;
                for (int i = 0 ; i < len ; ++i){
                    if (cookies[i].getName().equals(ISparksConstant.AUTHORIZATION)){
                        token = cookies[i].getValue();
                        break;
                    }
                }
            }
        }
        if (StringUtils.isEmpty(token)) {
            // 从属性获取
            Map<String, String[]> map = request.getParameterMap();
            String[] values = map.get(ISparksConstant.AUTHORIZATION);
            if(values != null && values.length > 0){
                token = values[0];
            }
        }
        return token;
    }

    private void write(HttpServletResponse response, String msg) {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            log.error("response io exception ", e);
        }
    }

    /**
     * 是否为异步请求
     */
    private boolean isAsync(HttpServletRequest request){
        return request.getRequestURI().startsWith("/api") || "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
