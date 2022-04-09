package app.isparks.core.service;



import app.isparks.core.pojo.dto.UserDTO;
import app.isparks.core.pojo.param.LoginParam;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author chenghd
 * @date 2020/8/3
 */
public interface IAdminService {

    /**
     * 登录
     *
     * @param loginName 用户名
     * @param password 密码
     * @param time 登录时长
     * @param timeUnit 登录时长
     */
    Optional<UserDTO> authenticate(String loginName , String password , long time , TimeUnit timeUnit);

    /**
     * 注销
     */
    boolean logout(String username);


}
