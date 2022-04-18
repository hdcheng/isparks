package app.isparks.service.impl;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.exception.NoFoundException;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.entity.User;
import app.isparks.core.security.jwt.exception.JWTException;
import app.isparks.core.service.IAdminService;
import app.isparks.core.service.ICacheService;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.*;
import app.isparks.core.service.support.BaseService;
import app.isparks.service.security.jwt.JwtHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 后台管理服务
 *
 * @author chenghd
 * @date 2020/8/3
 */

@Service
public class AdminServiceImpl extends BaseService implements IAdminService {

    private Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final static String AUTH_CACHE_KEY_PREFIX = ISparksConstant.AUTHORIZATION + ":";

    private IUserService userService;

    private ICacheService cacheService;

    public AdminServiceImpl(UserServiceImpl userService,CacheServiceImpl cacheService) {
        notNull(userService,"user service implement class can not be null");
        notNull(userService,"cache service implement class can not be null");

        this.userService = userService;
        this.cacheService = cacheService;
    }

    @Override
    public Optional<UserDTO> authenticate(String loginName, String password , long time , TimeUnit timeUnit) {
        notEmpty(loginName,"login name must not be empty");
        notEmpty(password,"password must not be empty");

        User user = ValidateUtils.isEmail(loginName) ?
                userService.getByEmail(loginName).orElseThrow(() -> new NoFoundException("邮箱不存在")) :
                userService.getByName(loginName).orElseThrow(() -> new NoFoundException("用户不存在"));

        boolean b = userService.passwordMatch(user, password);

        UserDTO userDTO = null;
        if (b) {
            //todo:audience记录客户端信息，比如ip，浏览器版本等，防止token被截取使用。
            Map<String,Object> claims = new HashMap<>();

            claims.put("user",user.getUserName());
            claims.put("email",user.getEmail());

            String token = "";

            try {
                token = JwtHandler.build().signJWT(claims,time,timeUnit);
            }catch (JWTException e){
                log.error("签发JWT异常",e);
                return Optional.empty();
            }
            userDTO = BeanUtils.copyProperties(user, UserDTO.class).withToken(token);

            String tokenId = JwtHandler.build().tryGetId(token);

            // TOKEN 缓存时长
            long TOKEN_CACHE_MILLIS;

            switch (timeUnit){
                case DAYS: TOKEN_CACHE_MILLIS =   time * 1000 * 60 * 60 * 24 ; break;
                case HOURS:TOKEN_CACHE_MILLIS =   time * 1000 * 60 * 60 ; break;
                case MINUTES:TOKEN_CACHE_MILLIS = time * 1000 * 60 ; break;
                case SECONDS:TOKEN_CACHE_MILLIS = time * 1000 ; break;
                case MICROSECONDS: TOKEN_CACHE_MILLIS = time ; break;
                default: TOKEN_CACHE_MILLIS = 1000 * 60 * 8;
            }

            cacheService.saveStringWithExpires(ISparksConstant.AUTHORIZATION + userDTO.getUserName(),tokenId,TOKEN_CACHE_MILLIS);

            // ip 跟 token 的 映射
            HttpServletRequest request= HttpUtils.getHttpServletRequest();
            String ip = IpUtils.obtainIp(request);
            if(!StringUtils.isEmpty(ip)){
                cacheService.saveStringWithExpires(AUTH_CACHE_KEY_PREFIX + userDTO.getUserName(),token,TOKEN_CACHE_MILLIS);
                cacheService.saveStringWithExpires(AUTH_CACHE_KEY_PREFIX + ip,token,TOKEN_CACHE_MILLIS);
            }
        }
        return Optional.ofNullable(userDTO);
    }

    @Override
    public boolean logout(String username) {
        notEmpty(username, "username must not be empty");

        return cacheService.invalidate(username);
    }

    @Override
    public Optional<String> authToken(String key) {
        if(key == null || key.isEmpty()){
            return Optional.empty();
        }
        String token = cacheService.getString(AUTH_CACHE_KEY_PREFIX + key);
        return (token == null || token.isEmpty())? Optional.empty() : Optional.ofNullable(token);
    }
}
