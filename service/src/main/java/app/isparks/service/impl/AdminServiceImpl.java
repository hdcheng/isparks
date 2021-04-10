package app.isparks.service.impl;

import app.isparks.core.dao.cache.AbstractCacheStore;
import app.isparks.core.exception.NoFoundException;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.entity.User;
import app.isparks.core.pojo.param.LoginParam;
import app.isparks.core.security.jwt.JwtHandler;
import app.isparks.core.security.jwt.exception.JWTException;
import app.isparks.core.service.IAdminService;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.BeanUtils;
import app.isparks.core.util.ValidateUtils;
import app.isparks.core.service.support.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    private IUserService userService;

    private AbstractCacheStore cacheStore;

    public AdminServiceImpl(UserServiceImpl userService, AbstractCacheStore cacheStore) {
        this.userService = userService;
        this.cacheStore = cacheStore;
    }

    @Override
    public Optional<UserDTO> authenticate(LoginParam loginParam) {
        notNull(loginParam, "login params must not be null");
        String loginName = loginParam.getLoginName();
        User user = ValidateUtils.isEmail(loginName) ?
                userService.getByEmail(loginName).orElseThrow(() -> new NoFoundException("邮箱不存在")) :
                userService.getByName(loginName).orElseThrow(() -> new NoFoundException("用户不存在"));

        //验证
        boolean b = userService.passwordMatch(user, loginParam.getPassword());
        UserDTO userDTO = null;
        if (b) {
            //todo:audience记录客户端信息，比如ip，浏览器版本等，防止token被截取使用。
            Map<String,Object> claims = new HashMap<>();

            claims.put("user",user.getUserName());
            claims.put("email",user.getEmail());

            String token = "";

            try {
                token = JwtHandler.build().signJWT(claims,8,TimeUnit.HOURS);
            }catch (JWTException e){
                log.error("签发JWT异常",e);
                return Optional.empty();
            }
            userDTO = BeanUtils.copyProperties(user, UserDTO.class).withToken(token);

            String tokenId = JwtHandler.build().tryGetId(token);

            cacheStore.put(userDTO.getUserName(),tokenId);

        }
        return Optional.ofNullable(userDTO);
    }


    @Override
    public boolean logout(String username) {
        notEmpty(username, "token must not be empty");
        return cacheStore.delete(username).isPresent();
    }

}