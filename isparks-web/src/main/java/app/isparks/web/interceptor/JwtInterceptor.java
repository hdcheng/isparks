package app.isparks.web.interceptor;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.pojo.enums.ResultType;
import app.isparks.core.service.IAdminService;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.IpUtils;
import app.isparks.core.util.JsonUtils;
import app.isparks.core.util.StringUtils;
import app.isparks.core.web.support.Result;
import app.isparks.core.web.support.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

/**
 * Jwt验证拦截器
 *
 * @author chenghd
 * @date 2020/8/4
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    private boolean open = true;

    private final String prefix = "Bearer ";

    private IUserService userService;

    private IAdminService adminService;

    private JwtInterceptor(){}

    public JwtInterceptor(IUserService userService,IAdminService adminService,boolean open){
        this(userService,adminService);
        this.open = open;
    }

    public JwtInterceptor(IUserService userService, IAdminService adminService){
        this.userService = userService;
        this.adminService = adminService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!open){return true;}

        String jwtToken = getTokenFromRequest(request);

        if(userService.verifyJwtToken(jwtToken)){
            return true;
        }

        if(!isAsyncRequest(request)){
            response.sendRedirect("admin/login");
            return false;
        }

        Result result = ResultUtils.build(ResultType.PERMISSION_FAILED).withMsg(ResultType.PERMISSION_FAILED.getMsg());

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Allow-Credentials","false");
        write(response, JsonUtils.toJson(result));

        return false;
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(ISparksConstant.AUTHORIZATION);

        if (StringUtils.isEmpty(token)) {
            // 从属性获取
            Map<String, String[]> map = request.getParameterMap();
            String[] values = map.get(ISparksConstant.AUTHORIZATION);
            if(values != null && values.length > 0){
                token = values[0];
            }
        }

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

        if(!StringUtils.isEmpty(token) && token.startsWith(prefix)){
            token = token.replace(prefix,"");
        }

//        if( StringUtils.isEmpty(token) && !isAsyncRequest(request)){
//            token = adminService.authToken(IpUtils.obtainIp(request)).orElse(null);
//        }

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


    private boolean isAsyncRequest(HttpServletRequest request){
        return !request.getRequestURI().startsWith("/admin");
    }

}
