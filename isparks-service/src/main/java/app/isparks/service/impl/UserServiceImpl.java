package app.isparks.service.impl;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.exception.AuthException;
import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.pojo.converter.ConverterFactory;
import app.isparks.core.pojo.converter.UserConverter;
import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.entity.User;
import app.isparks.core.pojo.param.UpdateUserParam;
import app.isparks.core.pojo.param.UserParam;
import app.isparks.core.service.IAdminService;
import app.isparks.core.service.ICacheService;
import app.isparks.core.service.IUserService;
import app.isparks.core.util.BeanUtils;
import app.isparks.core.util.MD5Utils;
import app.isparks.core.util.StringUtils;
import app.isparks.core.util.thread.LocalThreadUtils;
import app.isparks.dao.repository.UserCurd;
import app.isparks.dao.repository.impl.UserCurdImpl;
import app.isparks.service.base.AbstractService;
import app.isparks.service.security.jwt.JwtHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenghd
 * @date 2020/7/24
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserConverter CONVERTER = ConverterFactory.get(UserConverter.class);

    private final static String THREAD_USER_KEY = "thread-user-key-" + System.currentTimeMillis();

    private UserCurd userCurd;

    private static String DEFAULT_PASSWORD = "123456";

    private ICacheService cacheService;

    public UserServiceImpl(UserCurdImpl userCurd,CacheServiceImpl cacheService ) {
        super(userCurd);
        this.userCurd = userCurd;

        notNull(cacheService,"CacheServiceImpl must not be null");

        this.cacheService = cacheService;
    }

    @Override
    public boolean create(UserParam userParam) {
        Assert.notNull(userParam,"user must not be null.");

        User user = CONVERTER.map(userParam);

        String encryptPwd = encryptPassword(userParam.getPassword());
        user.setPassword(encryptPwd);

        return abstractInsert(user).isPresent();
    }

    @Override
    public boolean resetPwd(String userId) {
        Assert.hasLength(userId,"user id must not be empty.");
        Optional<User> opt = abstractGetById(userId);

        abstractGetById(userId).ifPresent((u)->{
            u.setPassword(defaultPassword());
        });

        return false;
    }


    @Override
    public boolean updatePwd(String userName, String oldPwd, String newPwd) {
        Assert.hasLength(userName,"user name must not be null.");
        Assert.hasLength(oldPwd,"old password must not be null.");
        Assert.hasLength(newPwd,"new password must not be null.");

        if (oldPwd.equals(newPwd)){
            resultMessage("新旧密码不能相同");
            return false;
        }

        User user = userCurd.selectByName(userName);
        if(user == null){
            resultMessage("用户不存在");
            return false;
        }

        if(!passwordMatch(user,oldPwd)){
            resultMessage("原密码错误");
            return false;
        }

        User newUser = new User();
        newUser.setId(user.getId());
        String encryptPwd = encryptPassword(newPwd);
        newUser.setPassword(encryptPwd);

        boolean success = abstractUpdate(newUser).isPresent();

        return success;
    }

    @Override
    public Optional<UserDTO> updateUserInfo(UpdateUserParam param) {
        notNull(param,"update param must not be null.");

        String id = param.getId();

        User user = abstractGetById(id).orElseThrow(() -> new InvalidValueException("用户不存在"));
        User newUser = (User) new User().withId(id);

        BeanUtils.updateProperties(param,newUser);
        abstractUpdate(newUser);

        BeanUtils.updateProperties(user,param);

        return Optional.ofNullable(CONVERTER.map(user));
    }

    @Override
    public List<UserDTO> listTos() {
        return CONVERTER.maps(abstractListAll());
    }

    @Override
    public Optional<User> getByEmail(String email) {
        Assert.hasLength(email,"email must not be empty.");
        return Optional.ofNullable(userCurd.selectByEmail(email));
    }

    @Override
    public Optional<User> getByName(String username) {
        Assert.hasLength(username, "user name must not be empty");
        return Optional.ofNullable(userCurd.selectByName(username));
    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        Assert.notNull(user, "user must not be null");
        Assert.hasLength(plainPassword, "password must not be empty");

        // 密码原文加密
        String encryptionPwd = encryptPassword(plainPassword);

        return encryptionPwd.equals(user.getPassword());
    }

    @Override
    public Optional<UserDTO> getCurrentUser() {

        String userName = getCurrentUserName().orElse("");

        User user = userCurd.selectByName(userName);

        return user == null ? Optional.empty() : Optional.ofNullable(CONVERTER.map(user));
    }

    @Override
    public Optional<String> getCurrentUserName() {
        return Optional.ofNullable(String.valueOf(LocalThreadUtils.getValue(THREAD_USER_KEY)));
    }

    @Override
    public boolean verifyJwtToken(String jwtToken) throws AuthException {

        if(!StringUtils.isEmpty(jwtToken)){
            try {
                Map<String, Object> claims = JwtHandler.build().getClaims(jwtToken);

                if(claims.size() <= 0){
                    log.info("JWT TOKEN内容为空");
                    return false;
                }

                String user = (String)claims.get("user");

                LocalThreadUtils.addValue(THREAD_USER_KEY,user);

                if(!StringUtils.isEmpty(user)){
                    String tokenId = cacheService.getString(ISparksConstant.AUTHORIZATION + user);
                    if(tokenId.equals(claims.get("jti"))){
                        return true;
                    }
                }

            }catch (Exception e){
                log.error("JWT验证异常");
            }
        }
        return false;
    }

    @Override
    public boolean beforeInsert(User user) {

        String password = user.getPassword();

        if(StringUtils.isEmpty(password)){
            String msg = "密码不能为空";
            resultMessage(msg);
            return false;
        }
        return true;
    }

    /**
     * 加密密码原文
     *
     * @param plainPassword 密码原文
     * @return 加密后的密文
     */
    private String encryptPassword(String plainPassword) {
        return MD5Utils.hashWithSalt(plainPassword);
    }

    /**
     * 重置密码时生成的默认密码
     *
     */
    private String defaultPassword(){return defaultPassword(null);}
    private String defaultPassword(User user){
        if (user != null && !StringUtils.isEmpty(user.getUserName())){
            return encryptPassword(user.getUserName() + DEFAULT_PASSWORD);
        }
        return encryptPassword(DEFAULT_PASSWORD);
    }
}
